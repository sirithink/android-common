package org.solovyev.android.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 4/15/12
 * Time: 6:01 PM
 */
public final class DbUtils {

    private DbUtils() {
        throw new AssertionError();
    }

    @NotNull
    public static <R> R doDbOperation(@NotNull SQLiteOpenHelper dbHelper, @NotNull DbOperation<R> operation) {
        final R result;

        SQLiteDatabase db = null;
        try {
            // open database
            db = dbHelper.getWritableDatabase();

            Cursor cursor = null;
            try {
                // open cursor
                cursor = operation.createCursor(db);
                // do operation
                result = operation.doOperation(cursor);
            } finally {
                // anyway if cursor was opened - close it
                if (cursor != null) {
                    cursor.close();
                }
            }
        } finally {
            // anyway if database was opened - close it
            if (db != null) {
                db.close();
            }
        }

        return result;
    }
}
