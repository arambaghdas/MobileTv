package train.apitrainclient.plugins;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import pl.tajchert.nammu.Nammu;

/**
 * Provides the File related utility and handy functions to manipulate files using this class
 */

public class FileUtils {

    /**
     * Provided is the context, Clones the image from source path to destination path and returns the results in the onsavecompletionlistener
     * @param context
     * @param sourcePath
     * @param destinationPath
     * @param onSaveImageCompletedListener
     */
    public static void copyImage(final Context context, final String sourcePath, final String destinationPath,
                                 final OnSaveImageCompletedListener onSaveImageCompletedListener) {
        Thread thread = new Thread() {
            @Override
            public void run() {

                try {
                    Bitmap bitmap = GetRotationAdjustedBitmap(sourcePath);
                    File file = new File(destinationPath);
                    file.getParentFile().mkdirs();
                    file.delete();
                    file.createNewFile();
                    FileOutputStream fos = new FileOutputStream(file);
                    // Use the compress method on the BitMap object to write image to the OutputStream
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (onSaveImageCompletedListener != null) {
                    DeleteTempDirectory();
                    onSaveImageCompletedListener.copyCompleted();
                }
            }
        };

        thread.start();
    }

    /**
     * Rotates the image bitmap at some angle
     * @param source bitmap to be rotated
     * @param angle angle to rotate bitmap
     * @return rotated Bitmap
     */
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    /**
     *
     * @param sourcePath
     * @return Rotation adjusted bitmap
     * @throws IOException needed to be handled on this method call
     */
    private static Bitmap GetRotationAdjustedBitmap(String sourcePath) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;
        Bitmap bitmap = BitmapFactory.decodeFile(sourcePath, options);

        ExifInterface ei = new ExifInterface(sourcePath);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        switch (orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                bitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                bitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                bitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:

            default:
                break;
        }
        return bitmap;
    }

    /**
     * Deletes the temp Directory
     */
    public static void DeleteTempDirectory() {
        if (Nammu.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            deleteAllFiles(new File(GetTempDirectory()));
        }
    }

    /**
     * Fetches the temp directory path
     * @return
     */
    public static String GetTempDirectory() {
        return new File(Environment.getExternalStorageDirectory(),
                "temp").getAbsolutePath();
    }

    /**
     * Deletes all the files in the
     * @param directory
     */
    private static void deleteAllFiles(File directory) {
        if(directory.exists() && directory.isDirectory() && directory.listFiles() != null) {
            for (File child : directory.listFiles()) {
                child.delete();
            }
        }
        directory.delete();
    }

    /**
     * interface to callback the image save completion
     */
    public interface OnSaveImageCompletedListener {
        void copyCompleted();
    }
}
