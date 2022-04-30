package com.example.manager_pneumo.ui.main.data;

import com.example.manager_pneumo.ui.main.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login( String password) {

        try {
            LoggedInUser fakeUser = null;
            System.out.println("passowrd=" + password);
            if (password.equals( "1111"))
            {
                // TODO: handle loggedInUser authentication
                 fakeUser =
                        new LoggedInUser(  "Jane Doe");
                return new Result.Success<>(fakeUser);
            } else
                throw new Exception("wrong password");
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}