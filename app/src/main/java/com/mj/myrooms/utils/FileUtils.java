package com.mj.myrooms.utils;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import static android.provider.ContactsContract.AUTHORITY;

import com.mj.myrooms.R;

public class FileUtils implements Parcelable {
    private static String TAG = "FileUtils";
    private static FileUtils instance;
    public final Creator<FileUtils> CREATOR = new Creator<FileUtils>() {
        public FileUtils createFromParcel(Parcel in) {
            return new FileUtils(in);
        }

        public FileUtils[] newArray(int size) {
            return new FileUtils[size];
        }
    };
    private int progress;
    private int currentFileSize;
    private int totalFileSize;

    public FileUtils() {
    }

    private FileUtils(Parcel in) {
        progress = in.readInt();
        currentFileSize = in.readInt();
        totalFileSize = in.readInt();
    }

    public static FileUtils getInstance() {
        if (instance == null) {
            instance = new FileUtils();
        }
        return instance;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getCurrentFileSize() {
        return currentFileSize;
    }

    public void setCurrentFileSize(int currentFileSize) {
        this.currentFileSize = currentFileSize;
    }

    public int getTotalFileSize() {
        return totalFileSize;
    }

    public void setTotalFileSize(int totalFileSize) {
        this.totalFileSize = totalFileSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(progress);
        dest.writeInt(currentFileSize);
        dest.writeInt(totalFileSize);
    }

    /**
     * create temporary image file
     *
     * @param mContext
     * @return
     */
    public File createTempImageFile(Context mContext) {
        File outputDir = mContext.getCacheDir();
        File outputFile = null;
        try {
            // create an image file name
            String timeStamp = DateTimeUtils.getInstance().formatDateTime(new Date(), DateTimeUtils.DateFormats.yyyyMMddHHmmss.getLabel());
            outputFile = File.createTempFile("tempImage_" + timeStamp, ".png", outputDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFile;
    }

    /**
     * save bitmap to file
     *
     * @param bitmap
     * @param filePath
     * @return
     */
    public String saveBitmapToFile(Bitmap bitmap, String filePath) {
        File file = new File(filePath);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    /**
     * get real path from uri
     *
     * @param mContext
     * @param uri
     * @return
     */
    public String getPathFromUri(Context mContext, Uri uri) {
        try {
            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            e.printStackTrace();
            return uri.getPath();
        }
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.<br>
     * <br>
     * Callers should check whether the path is local before assuming it
     * represents a local file.
     *
     * @param mContext The context.
     * @param uri      The Uri to query.
     */
    public String getPath(final Context mContext, final Uri uri) {
        String absolutePath = getLocalPath(mContext, uri);
        return absolutePath != null ? absolutePath : uri.toString();
    }

    private String getLocalPath(final Context mContext, final Uri uri) {
        Debugger.logD(TAG + " File -",
                "Authority: " + uri.getAuthority() +
                        ", Fragment: " + uri.getFragment() +
                        ", Port: " + uri.getPort() +
                        ", Query: " + uri.getQuery() +
                        ", Scheme: " + uri.getScheme() +
                        ", Host: " + uri.getHost() +
                        ", Segments: " + uri.getPathSegments().toString());

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(mContext, uri)) {
            // LocalStorageProvider
            if (isLocalStorageDocument(uri)) {
                // The path is the id
                return DocumentsContract.getDocumentId(uri);
            }
            // ExternalStorageProvider
            else if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                } else if ("home".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/documents/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                if (id != null && id.startsWith("raw:")) {
                    return id.substring(4);
                }

                String[] contentUriPrefixesToTry = new String[]{
                        "content://downloads/public_downloads",
                        "content://downloads/my_downloads"
                };

                for (String contentUriPrefix : contentUriPrefixesToTry) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.valueOf(id));
                    try {
                        String path = getDataColumn(mContext, contentUri, null, null);
                        if (path != null) {
                            return path;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // path could not be retrieved using ContentResolver, therefore copy file to accessible cache using streams
                String fileName = getFileName(mContext, uri);
                File cacheDir = getDocumentCacheDir(mContext);
                File file = generateFileName(fileName, cacheDir);
                String destinationPath = null;
                if (file != null) {
                    destinationPath = file.getAbsolutePath();
                    saveFileFromUri(mContext, uri, destinationPath);
                }

                return destinationPath;
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(mContext, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }

            return getDataColumn(mContext, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is local.
     */
    public boolean isLocalStorageDocument(Uri uri) {
        return AUTHORITY.equals(uri.getAuthority());
    }

    /**
     * @param uri
     * @return
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     * @return
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     * @return
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     * @return
     */
    public boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * @param mContext
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    public String getDataColumn(Context mContext, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = mContext.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return null;
    }

    /**
     * get filename
     *
     * @param context
     * @param uri
     * @return
     */
    public String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf(File.separator);
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    /**
     * get document cache directory directory
     *
     * @param context
     * @return
     */
    public File getDocumentCacheDir(@NonNull Context context) {
        File dir = context.getCacheDir();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }

    /**
     * generate file name
     *
     * @param name
     * @param directory
     * @return
     */
    @Nullable
    public File generateFileName(@Nullable String name, File directory) {
        if (name == null) {
            return null;
        }

        File file = new File(directory, name);

        if (file.exists()) {
            String fileName = name;
            String extension = "";
            int dotIndex = name.lastIndexOf('.');
            if (dotIndex > 0) {
                fileName = name.substring(0, dotIndex);
                extension = name.substring(dotIndex);
            }

            int index = 0;

            while (file.exists()) {
                index++;
                name = fileName + '(' + index + ')' + extension;
                file = new File(directory, name);
            }
        }

        try {
            if (!file.createNewFile()) {
                return null;
            }
        } catch (IOException e) {
            Debugger.logE(TAG, e.getMessage());
            return null;
        }

        return file;
    }

    /**
     * save file from uri
     *
     * @param mContext
     * @param uri
     * @param destinationPath
     */
    private void saveFileFromUri(Context mContext, Uri uri, String destinationPath) {
        InputStream is = null;
        BufferedOutputStream bos = null;
        try {
            is = mContext.getContentResolver().openInputStream(uri);
            bos = new BufferedOutputStream(new FileOutputStream(destinationPath, false));
            byte[] buf = new byte[1024];
            is.read(buf);
            do {
                bos.write(buf);
            } while (is.read(buf) != -1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (bos != null) bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * create temp file - image
     *
     * @param mContext
     * @return
     * @throws IOException
     */
    public File createTempProfileImageFile(Context mContext) throws IOException {
        File tempFile;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    "MyRooms" + "/camera");

            tempFile = new File(Environment.getExternalStorageDirectory(),
                    "MyRooms" + "/camera");
        } else {
            tempFile = mContext.getCacheDir();
        }

        // remove folder
        String[] children = tempFile.list();
        if (children != null && children.length > 0) {
            for (int i = 0; i < children.length; i++) {
                new File(tempFile, children[i]).delete();
            }
            if (tempFile.isDirectory()) {
                tempFile.delete();
            }
        }

        // create folder
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }

        return new File(tempFile, String.valueOf(System.currentTimeMillis()) + ".jpg");
    }

    /**
     * shrink bitmap
     *
     * @param file
     * @return
     */
    public Bitmap ShrinkBitmap(String file) {
        try {
            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

            bmpFactoryOptions.inSampleSize = 1;
            bmpFactoryOptions.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

            ExifInterface exif = new ExifInterface(file);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Debugger.logE(TAG, "orientation : " + orientation);

            // create a matrix object
            Matrix matrix = new Matrix();
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
                matrix.postRotate(-90); // anti-clockwise by 90 degrees
            else if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
                matrix.postRotate(180); // clockwise by 180 degrees
            else if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
                matrix.postRotate(90); // clockwise by 90 degrees
            else
                matrix.postRotate(0); // clockwise by 0 degrees

            // create a new bitmap from the original using the matrix to transform the result
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            return rotatedBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * remove folder
     */
    public void removeTempFolder(Context mContext) {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            File tempfile = new File(android.os.Environment.getExternalStorageDirectory(),
                    "MyRooms"+ "/camera");

            String[] children = tempfile.list();
            if (children != null && children.length > 0) {
                for (int i = 0; i < children.length; i++) {
                    new File(tempfile, children[i]).delete();
                }
                if (tempfile.isDirectory()) {
                    tempfile.delete();
                }
            }
        }
    }

    /**
     * Get the Intent for camera
     *
     * @return The intent for opening a camera
     */
    public Intent intentForCamera(Context mContext, File photoFile) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoURI = FileProvider.getUriForFile(mContext,
                mContext.getPackageName() + ".provider",
                photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

        return intent;
    }

    /**
     * Get the Intent for selecting image to be used in an Intent Chooser.
     *
     * @return The intent for opening a file with Intent.createChooser()
     */
    public Intent intentForGallery() {
        String[] mimeTypes = {"image/*"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }

        return intent;
    }
}
