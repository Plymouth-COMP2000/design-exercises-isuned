package com.example.hqrestaurant;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Api {

    // ✅ API ROOT (NOT /docs)
    private static final String BASE_URL = "http://10.240.72.69/comp2000/coursework";
    public static final String STUDENT_ID = "10897390";

    private static RequestQueue requestQueue;

    // -----------------------------
    // Queue
    // -----------------------------
    private static void initQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }

    // -----------------------------
    // Logging helper
    // -----------------------------
    private static void logVolleyError(String tag, VolleyError error) {
        if (error == null) return;

        if (error.networkResponse != null && error.networkResponse.data != null) {
            Log.e(tag, new String(error.networkResponse.data));
        } else {
            Log.e(tag, String.valueOf(error.getMessage()));
        }
    }

    // -----------------------------
    // Convert User -> JSON
    // (match API schema: username, email, password)
    // -----------------------------
    public static JSONObject userToJson(User user) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("username", user.username);
            obj.put("email", user.email);
            obj.put("password", user.password);

            // ❌ Do NOT send role unless the API schema includes it
            // obj.put("role", user.role);

        } catch (JSONException e) {
            Log.e("API", "userToJson error: " + e.getMessage());
        }
        return obj;
    }

    // =========================================================
    // 1) CREATE STUDENT DB
    // POST /create_student/{student_id}
    // =========================================================
    public static void createStudentDb(Context context,
                                       Response.Listener<String> onSuccess,
                                       Response.ErrorListener onFail) {

        initQueue(context);

        String url = BASE_URL + "/create_student/" + STUDENT_ID;

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    Log.d("API", "Student DB created: " + response);
                    if (onSuccess != null) onSuccess.onResponse(response);
                },
                error -> {
                    logVolleyError("API_CREATE_STUDENT", error);
                    if (onFail != null) onFail.onErrorResponse(error);
                }
        );

        requestQueue.add(request);
    }

    // =========================================================
    // 2) CREATE USER
    // POST /create_user/{student_id}
    // Body: JSON user object
    // =========================================================
    public static void createUser(Context context,
                                  JSONObject userJson,
                                  Response.Listener<JSONObject> onSuccess,
                                  Response.ErrorListener onFail) {

        initQueue(context);

        String url = BASE_URL + "/create_user/" + STUDENT_ID;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                userJson,
                response -> {
                    Log.d("API", "User created: " + response);
                    if (onSuccess != null) onSuccess.onResponse(response);
                },
                error -> {
                    logVolleyError("API_CREATE_USER", error);
                    if (onFail != null) onFail.onErrorResponse(error);
                }
        );

        requestQueue.add(request);
    }

    // ✅ Helper: Register user safely (create DB then create user)
    public static void registerUser(Context context,
                                    User user,
                                    Runnable onSuccess,
                                    Runnable onFail) {

        JSONObject userJson = userToJson(user);

        createStudentDb(context,
                response -> createUser(context, userJson,
                        res -> { if (onSuccess != null) onSuccess.run(); },
                        err -> { if (onFail != null) onFail.run(); }
                ),
                err -> { if (onFail != null) onFail.run(); }
        );
    }

    // =========================================================
    // 3) READ ONE USER
    // GET /read_user/{student_id}/{username}
    // =========================================================
    public static void readUser(Context context,
                                String username,
                                Response.Listener<JSONObject> onSuccess,
                                Response.ErrorListener onFail) {

        initQueue(context);

        String url = BASE_URL + "/read_user/" + STUDENT_ID + "/" + username;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    Log.d("API", "User read: " + response);
                    if (onSuccess != null) onSuccess.onResponse(response);
                },
                error -> {
                    logVolleyError("API_READ_USER", error);
                    if (onFail != null) onFail.onErrorResponse(error);
                }
        );

        requestQueue.add(request);
    }

    // =========================================================
    // 4) READ ALL USERS
    // GET /read_all_users/{student_id}
    // =========================================================
    public static void readAllUsers(Context context,
                                    Response.Listener<JSONArray> onSuccess,
                                    Response.ErrorListener onFail) {

        initQueue(context);

        String url = BASE_URL + "/read_all_users/" + STUDENT_ID;

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    Log.d("API", "All users: " + response);
                    if (onSuccess != null) onSuccess.onResponse(response);
                },
                error -> {
                    logVolleyError("API_READ_ALL_USERS", error);
                    if (onFail != null) onFail.onErrorResponse(error);
                }
        );

        requestQueue.add(request);
    }

    // =========================================================
    // 5) UPDATE USER
    // PUT /update_user/{student_id}/{username}
    // =========================================================
    public static void updateUser(Context context,
                                  String username,
                                  JSONObject userJson,
                                  Response.Listener<JSONObject> onSuccess,
                                  Response.ErrorListener onFail) {

        initQueue(context);

        String url = BASE_URL + "/update_user/" + STUDENT_ID + "/" + username;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                userJson,
                response -> {
                    Log.d("API", "User updated: " + response);
                    if (onSuccess != null) onSuccess.onResponse(response);
                },
                error -> {
                    logVolleyError("API_UPDATE_USER", error);
                    if (onFail != null) onFail.onErrorResponse(error);
                }
        );

        requestQueue.add(request);
    }

    // =========================================================
    // 6) DELETE USER
    // DELETE /delete_user/{student_id}/{username}
    // =========================================================
    public static void deleteUser(Context context,
                                  String username,
                                  Response.Listener<String> onSuccess,
                                  Response.ErrorListener onFail) {

        initQueue(context);

        String url = BASE_URL + "/delete_user/" + STUDENT_ID + "/" + username;

        StringRequest request = new StringRequest(
                Request.Method.DELETE,
                url,
                response -> {
                    Log.d("API", "User deleted: " + response);
                    if (onSuccess != null) onSuccess.onResponse(response);
                },
                error -> {
                    logVolleyError("API_DELETE_USER", error);
                    if (onFail != null) onFail.onErrorResponse(error);
                }
        );

        requestQueue.add(request);
    }
}


