package com.glassait.androidproject.common.utils.file;

import android.content.Context;

import com.glassait.androidproject.common.utils.secret.Secret;
import com.glassait.androidproject.model.dao.UserDao;
import com.glassait.androidproject.model.database.AppDatabase;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.User;
import com.glassait.androidproject.view.start.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * This class is a runnable used for get and check the user file (file who store the email, id
 * and uuid of the user).
 * <p>
 * For an example of used check {@link MainActivity}
 */
public class GetUserFromFile implements Runnable {
    private       User    user;
    private final Context context;

    /**
     * @param context The context of the application
     */
    public GetUserFromFile(Context context) {this.context = context;}

    /**
     * @return The user found if the user file is ok, null otherwise.
     */
    public User getUser() {
        return user;
    }

    /**
     * First step: check if the cache file exist and has some data stored inside
     * <p>
     * Second step: Transform the result in JSONObject
     * <p>
     * Third step: Check inside de database is the user exist
     * <p>
     * Last step: done some final check and store the result
     */
    @Override
    public void run() {
        // First step
        Cache cache = new Cache(
                Secret.USER_FILE,
                context
        );

        String result = cache.readDataFromFile();
        if (result == null) {
            user = null;
            return;
        }

        try {
            // Second step
            JSONObject data = new JSONObject(result);
            AppDatabase database = Builder.getInstance()
                                          .getAppDatabase();

            if (data.length() <= 0) {
                user = null;
                return;
            }

            UserDao userDao = database.userDao();
            User[]  us      = new User[1];
            String  email   = data.getString("email");

            // Third step
            userDao.getUserFromEmail(email)
                   .subscribe(
                           u -> us[0] = u,
                           throwable -> {
                               System.out.println(
                                       "No user register with this " + "email: " + email);
                               cache.deleteFile();
                           }
                   )
                   .dispose();

            // Last step
            if (us[0] != null && us[0].uuid.equals(UUID.fromString(data.getString("uuid")))) {
                user = us[0];
                return;
            }
            user = null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}