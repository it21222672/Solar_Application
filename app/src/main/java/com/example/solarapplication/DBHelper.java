package com.example.solarapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DataBase";
    private static final String TABLE1 = "UserInfo";
    private static final String TABLE2 = "Products";
    private static final String TABLE3 = "Supplier";
    private static final String TABLE4 = "Feedback";
    private static final String TABLE5 = "Articles";
    private static final String TABLE6 = "Journalist";
    private static final String TABLE7 = "Orders";
    private static final String TABLE8 = "Inquiries";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table1 = "CREATE TABLE "+TABLE1+"(Id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT,Email TEXT, PNumber TEXT, Password TEXT)";
        String table2 = "CREATE TABLE "+TABLE2+"(Id INTEGER PRIMARY KEY AUTOINCREMENT,image BLOB not null, ProductName  VARCHAR,BrandName VARCHAR,Price VARCHAR, Description TEXT,Category VARCHAR,WarrantyPeriod VARCHAR,Request VARCHAR,SupplierId INTEGER)";
        String table3 = "CREATE TABLE "+TABLE3+"(Id INTEGER PRIMARY KEY AUTOINCREMENT,image BLOB not null,Company_Name VARCHAR,Registration_No VARCHAR, Address VARCHAR, Name VARCHAR,Position VARCHAR, Email VARCHAR, Web VARCHAR,Contact_no VARCHAR,Description TEXT,Request VARCHAR, Password TEXT)";
        String table4 = "CREATE TABLE "+TABLE4+"(Id INTEGER PRIMARY KEY AUTOINCREMENT,Feedback TEXT,UserId INTEGER, Likes FLOAT, Status VARCHAR)";
        String table5 = "CREATE TABLE "+TABLE5+"(Id INTEGER PRIMARY KEY AUTOINCREMENT,image BLOB not null, Title  VARCHAR, Description TEXT,Status VARCHAR,JournalistId INTEGER)";
        String table6 = "CREATE TABLE "+TABLE6+"(Id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT,Email TEXT, PNumber TEXT,NicNumber VARCHAR, Password TEXT)";
        String table7 = "CREATE TABLE "+TABLE7+"(Id INTEGER PRIMARY KEY AUTOINCREMENT,image BLOB not null, ProductName VARCHAR,Quantity VARCHAR, CustomerId INTEGER,UserName VARCHAR,Email VARCHAR,ContactNumber VARCHAR, Address VARCHAR, PostalCode VARCHAR,Date VARCHAR,Description TEXT,Status VARCHAR,SupplierId INTEGER)";
        String table8 = "CREATE TABLE "+TABLE8+"(Id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT,Email TEXT, Inquiry TEXT)";
        db.execSQL(table1);
        db.execSQL(table2);
        db.execSQL(table3);
        db.execSQL(table4);
        db.execSQL(table5);
        db.execSQL(table6);
        db.execSQL(table7);
        db.execSQL(table8);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //drop tables if existed
        db.execSQL("DROP TABLE IF EXISTS "+TABLE1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE3);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE4);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE5);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE6);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE7);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE8);
        // creating tables again
        onCreate(db);
    }
    public boolean insertInquiries(String Username, String Email, String Inquiry) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username", Username);
        contentValues.put("Email", Email);
        contentValues.put("Inquiry", Inquiry);
        long result = sqLiteDatabase.insert(TABLE8, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public ArrayList<Inquiries> getAllInquiries() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Inquiries> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE8, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String username = cursor.getString(1);
            String Email = cursor.getString(2);
            String Inquiry = cursor.getString(3);
            Inquiries article = new Inquiries(id, username, Email, Inquiry);
            arrayList.add(article);
        }

        cursor.close(); // Close the cursor when done to release resources
        return arrayList;
    }
    public int getInquiriesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        int count = 0;
        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE8, null);
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return count;
    }

    public Boolean RemoveOder(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Orders where Id = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            long result = db.delete("Orders", "Id=?", new String[]
                    {String.valueOf(id)});
            return result != -1;
        } else {
            return false;
        }
    }
    public Boolean RemoveInqury(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Inquiries where Id = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            long result = db.delete("Inquiries", "Id=?", new String[]
                    {String.valueOf(id)});
            return result != -1;
        } else {
            return false;
        }
    }
    public boolean insertOrder(byte[] image,String ProductName, String Quantity,int CustomerId, String UserName, String Email,String ContactNumber,String Address, String PostalCode, String Date,String Description,  String Status,int SupplierId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("image", image);
        contentValues.put("ProductName", ProductName);
        contentValues.put("Quantity", Quantity);
        contentValues.put("CustomerId",CustomerId);
        contentValues.put("UserName", UserName);
        contentValues.put("Email", Email);
        contentValues.put("ContactNumber" ,ContactNumber);
        contentValues.put("Address", Address);
        contentValues.put("PostalCode", PostalCode);
        contentValues.put("Date", Date);
        contentValues.put("Description",Description);
        contentValues.put("Status", Status);
        contentValues.put("SupplierId",SupplierId);

        long result = sqLiteDatabase.insert(TABLE7, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Boolean updateOrderStatus(int id,String Status) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Status", Status);
        Cursor cursor=DB.rawQuery("Select*from Orders where Id=?",new String[]{String.valueOf(id)});
        if (cursor.getCount()>0) {
            long result = DB.update("Orders", contentValues, "Id=?", new String[]{String.valueOf(id)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }
    public ArrayList<Orders> getCustomerOrders(int UserId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Orders> arrayList = new ArrayList<>();
        String[] selectionArgs = {String.valueOf(UserId)};
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE7 + " WHERE CustomerId = ?", selectionArgs);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            byte[] img = cursor.getBlob(1);
            String ProductName = cursor.getString(2);
            String Quantity = cursor.getString(3);
            int CustomerId = cursor.getInt(4);
            String UserName = cursor.getString(5);
            String Email = cursor.getString(6);
            String ContactNumber = cursor.getString(7);
            String Address = cursor.getString(8);
            String PostalCode = cursor.getString(9);
            String Date = cursor.getString(10);
            String Description = cursor.getString(11);
            String Status = cursor.getString(12);
            int SupplierId = cursor.getInt(13);

            Orders orders = new Orders(id, img, ProductName, Quantity, CustomerId, UserName, Email, ContactNumber, Address, PostalCode, Date, Description, Status, SupplierId);
            arrayList.add(orders);
        }

        cursor.close(); // Close the cursor when done to release resources

        // Reverse the ArrayList to display from bottom to top
        Collections.reverse(arrayList);

        return arrayList;
    }

    public ArrayList<Orders> getSupplerOrders(int UserId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Orders> arrayList = new ArrayList<>();
        String[] selectionArgs = {String.valueOf(UserId)};
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE7+ " WHERE SupplierId = ?", selectionArgs);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            byte[] img = cursor.getBlob(1);
            String ProductName = cursor.getString(2);
            String Quantity = cursor.getString(3);
            int CustomerId = cursor.getInt(4);
            String UserName = cursor.getString(5);
            String Email = cursor.getString(6);
            String ContactNumber = cursor.getString(7);
            String Address = cursor.getString(8);
            String PostalCode = cursor.getString(9);
            String Date = cursor.getString(10);
            String Description=cursor.getString(11);
            String Status = cursor.getString(12);
            int SupplierId = cursor.getInt(13);

            Orders orders = new Orders(id, img, ProductName, Quantity, CustomerId,UserName,Email,ContactNumber,Address,PostalCode,Date,Description,Status, SupplierId);
            arrayList.add(orders);
        }

        cursor.close(); // Close the cursor when done to release resources
        Collections.reverse(arrayList);
        return arrayList;
    }
    public boolean PublishAnArticle(byte[] image,String Title, String Description,String Status, int JournalistId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("image", image);
        contentValues.put("Title", Title);
        contentValues.put("Description", Description);
        contentValues.put("Status", Status);
        contentValues.put("JournalistId", JournalistId);
        try {
            long result = sqLiteDatabase.insert(TABLE5, null, contentValues);

            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public Boolean updateArticlesData(int id,byte[] image,String Title, String Description,String Status, int JournalistId) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("image", image);
        contentValues.put("Title", Title);
        contentValues.put("Description", Description);
        contentValues.put("Status", Status);
        contentValues.put("JournalistId", JournalistId);

        Cursor cursor=DB.rawQuery("Select*from Articles where Id=?",new String[]{String.valueOf(id)});
        if (cursor.getCount()>0) {
            long result = DB.update("Articles", contentValues, "Id=?", new String[]{String.valueOf(id)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }
    public Boolean RemoveNews(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Articles where Id = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            long result = db.delete("Articles", "Id=?", new String[]
                    {String.valueOf(id)});
            return result != -1;
        } else {
            return false;
        }
    }
    public ArrayList<Article> getJournalistNews(int UserId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Article> arrayList = new ArrayList<>();
        String[] selectionArgs = {String.valueOf(UserId)};
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE5 + " WHERE JournalistId = ?", selectionArgs);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            byte[] img = cursor.getBlob(1);
            String Title = cursor.getString(2);
            String Description = cursor.getString(3);
            String Status = cursor.getString(4);
            int JournalistId = cursor.getInt(5);

            Article article = new Article(id, img, Title, Description, Status, JournalistId);
            arrayList.add(article);
        }
        // Reverse the ArrayList
        Collections.reverse(arrayList);

        return arrayList;
    }
    public ArrayList<Article> getAllNewsPost() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Article> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE5, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            byte[] img = cursor.getBlob(1);
            String Title = cursor.getString(2);
            String Description = cursor.getString(3);
            String Status = cursor.getString(4);
            int JournalistId = cursor.getInt(5);

            Article article = new Article(id, img, Title, Description, Status, JournalistId);
            arrayList.add(article);
        }

        cursor.close(); // Close the cursor when done to release resources
        return arrayList;
    }
    public ArrayList<Article> searchAllArticles(String keyword) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Article> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Articles WHERE Status LIKE ?", new String[]{"%" + keyword + "%"});

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            byte[] img = cursor.getBlob(1);
            String Title = cursor.getString(2);
            String Description = cursor.getString(3);
            String Status = cursor.getString(4);
            int JournalistId = cursor.getInt(5);

            Article article = new Article(id, img, Title, Description, Status, JournalistId);
            arrayList.add(article);
        }
        cursor.close(); // Close the cursor when done to release resources
        return arrayList;
    }
    public ArrayList<Article> ViewApprovedArticles() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Article> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Articles WHERE Status = ? ORDER BY id DESC", new String[]{"Approved"});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            byte[] img = cursor.getBlob(1);
            String Title = cursor.getString(2);
            String Description = cursor.getString(3);
            String Status = cursor.getString(4);
            int JournalistId = cursor.getInt(5);

            Article article = new Article(id, img, Title, Description, Status, JournalistId);
            arrayList.add(article);
        }

        cursor.close(); // Close the cursor when done to release resources
        return arrayList;
    }
    public ArrayList<Article> SearchApprovedArticles(String keyword) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Article> arrayList = new ArrayList<>();

        // Define the SQL query to retrieve approved articles that match the keyword
        String query = "SELECT * FROM Articles WHERE Status = 'Approved' " +
                "AND (Title LIKE ? OR Description LIKE ?) " +
                "ORDER BY id DESC";

        // Arguments for the query
        String[] selectionArgs = {"%" + keyword + "%", "%" + keyword + "%"};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            byte[] img = cursor.getBlob(1);
            String Title = cursor.getString(2);
            String Description = cursor.getString(3);
            String Status = cursor.getString(4);
            int JournalistId = cursor.getInt(5);

            Article article = new Article(id, img, Title, Description, Status, JournalistId);
            arrayList.add(article);
        }

        cursor.close();
        return arrayList;
    }

    public Boolean updateNewsStatus(int id,String Status) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Status", Status);
        Cursor cursor=DB.rawQuery("Select*from Articles where Id=?",new String[]{String.valueOf(id)});
        if (cursor.getCount()>0) {
            long result = DB.update("Articles", contentValues, "Id=?", new String[]{String.valueOf(id)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }

    public boolean insertFeedback(String Feedback, int UserId, float Likes, String Status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Feedback", Feedback);
        contentValues.put("UserId", UserId);
        contentValues.put("Likes", Likes);
        contentValues.put("Status", Status);
        long result = sqLiteDatabase.insert(TABLE4, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public ArrayList<Feedback> getUserFeedback(int Id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Feedback> arrayList = new ArrayList<>();
        String[] selectionArgs = {String.valueOf(Id)};
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE4 + " WHERE UserId = ?", selectionArgs);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String Feedback = cursor.getString(1);
            int UserId = cursor.getInt(2);
            float Likes = cursor.getInt(3);
            String Status = cursor.getString(4);

            Feedback feedback = new Feedback(id, Feedback, UserId, Likes, Status);
            arrayList.add(feedback);
        }

        // Reverse the ArrayList to display data from bottom to top
        Collections.reverse(arrayList);

        return arrayList;
    }
    public ArrayList<Feedback> searchApprovedFeedback() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Feedback> arrayList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM Feedback WHERE Status = ? ORDER BY id DESC", new String[]{"Approved"});

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String feedbackText = cursor.getString(1);
            int userId = cursor.getInt(2);
            float likes = cursor.getInt(3);
            String status = cursor.getString(4);

            Feedback feedback = new Feedback(id, feedbackText, userId, likes, status);
            arrayList.add(feedback);
        }

        return arrayList;
    }
    public ArrayList<Feedback> searchAllFeedback(String keyword) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Feedback> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Feedback WHERE Status LIKE ?", new String[]{"%" + keyword + "%"});
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String Feedback = cursor.getString(1);
            int UserId = cursor.getInt(2);
            float Likes = cursor.getInt(3);
            String Status = cursor.getString(4);

            Feedback feedback = new Feedback(id, Feedback, UserId, Likes, Status);
            arrayList.add(feedback);

        }
        return arrayList;
    }
    public ArrayList<Feedback> getadminFeedback() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Feedback> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE4 ,null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String Feedback = cursor.getString(1);
            int UserId = cursor.getInt(2);
            float Likes = cursor.getInt(3);
            String Status = cursor.getString(4);

            Feedback feedback = new Feedback(id, Feedback, UserId, Likes, Status);
            arrayList.add(feedback);
        }

        // Reverse the ArrayList to display data from bottom to top
        Collections.reverse(arrayList);

        return arrayList;
    }
    public Boolean updateFeedbackStatus(int id,String Status) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Status", Status);
        Cursor cursor=DB.rawQuery("Select*from Feedback where Id=?",new String[]{String.valueOf(id)});
        if (cursor.getCount()>0) {
            long result = DB.update("Feedback", contentValues, "Id=?", new String[]{String.valueOf(id)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }
    public Boolean RemoveFeedback(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE4 + " where Id = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            long result = db.delete("Feedback", "Id=?", new String[]
                    {String.valueOf(id)});
            return result != -1;
        } else {
            return false;
        }
    }
    public boolean insertUser(String Username, String Email, String TpNumber, String Password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username", Username);
        contentValues.put("Email", Email);
        contentValues.put("PNumber", TpNumber);
        contentValues.put("Password", Password);
        long result = sqLiteDatabase.insert(TABLE1, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean insertJournalist(String Username, String Email, String TpNumber,String NicNumber, String Password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username", Username);
        contentValues.put("Email", Email);
        contentValues.put("PNumber", TpNumber);
        contentValues.put("NicNumber", NicNumber);
        contentValues.put("Password", Password);
        long result = sqLiteDatabase.insert(TABLE6, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Boolean checkEmailJournalist(String Email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Journalist where Email = ?", new String [] {Email});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public ArrayList<Journalist> ValidLoginJournalist(String UserId, String Password) {
        ArrayList<Journalist> userList = new ArrayList<Journalist>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("Select * from Journalist where Email ='" + UserId + "' and Password ='" + Password + "'", null);
            if (cursor.moveToFirst()) {
                do {
                    Journalist user = new Journalist();
                    user.setId(cursor.getInt(0));
                    user.setUsername(cursor.getString(1));
                    user.setEmail(cursor.getString(2));
                    user.setTpNumber(cursor.getString(3));
                    user.setNicNumber(cursor.getString(4));
                    user.setPassword(cursor.getString(5));

                    userList.add(user);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userList;
    }
    public Boolean checkEmail(String Email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from UserInfo where Email = ?", new String [] {Email});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public ArrayList<Account> ValidLogin(String UserId, String Password) {
        ArrayList<Account> userList = new ArrayList<Account>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("Select * from UserInfo where Email ='" + UserId + "' and Password ='" + Password + "'", null);
            if (cursor.moveToFirst()) {
                do {
                    Account user = new Account();
                    user.setId(cursor.getInt(0));
                    user.setUsername(cursor.getString(1));
                    user.setEmail(cursor.getString(2));
                    user.setTpNumber(cursor.getString(3));
                    user.setPassword(cursor.getString(4));
                    userList.add(user);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userList;
    }
    public ArrayList<Suppliers> ValidLogin2(String UserId, String Password) {
        ArrayList<Suppliers> userList = new ArrayList<Suppliers>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT Id, Image, Name, Email FROM Supplier WHERE Email = ? AND Password = ? AND Request = 'Approved'",
                    new String[]{UserId, Password});
            if (cursor.moveToFirst()) {
                do {
                    Suppliers user = new Suppliers();
                    user.setId(cursor.getInt(0));
                    user.setImage(cursor.getBlob(1));
                    user.setName(cursor.getString(2));
                    user.setEmail(cursor.getString(3));
                    userList.add(user);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userList;
    }
    public Suppliers getSupplierById(int supplierId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Suppliers supplier = null;

        Cursor cursor = db.rawQuery("SELECT * FROM Supplier WHERE Id = ?", new String[]{String.valueOf(supplierId)});

        if (cursor.moveToFirst()) {
            supplier = new Suppliers();
            supplier.setId(cursor.getInt(0));
            supplier.setImage(cursor.getBlob(1));
            supplier.setCompany_Name(cursor.getString(2));
            supplier.setRegistration_No(cursor.getString(3));
            supplier.setAddress(cursor.getString(4));
            supplier.setName(cursor.getString(5));
            supplier.setPosition(cursor.getString(6));
            supplier.setEmail(cursor.getString(7));
            supplier.setWeb(cursor.getString(8));
            supplier.setContact_no(cursor.getString(9));
            supplier.setDescription(cursor.getString(10));
            supplier.setPassword(cursor.getString(11));
        }
        cursor.close();

        return supplier; // Return null if the supplier is not found in the database
    }
    public boolean insertProduct(byte[] image, String ProductName, String BrandName, String Price, String Description, String Category, String WarrantyPeriod, String Request, int SupplierId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("image", image);
        contentValues.put("ProductName", ProductName);
        contentValues.put("BrandName", BrandName);
        contentValues.put("Price", Price);
        contentValues.put("Description", Description);
        contentValues.put("Category", Category);
        contentValues.put("WarrantyPeriod", WarrantyPeriod);
        contentValues.put("Request", Request);
        contentValues.put("SupplierId", SupplierId);
        try {
            long result = sqLiteDatabase.insert(TABLE2, null, contentValues);

            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public Boolean updateProductData(int id, byte[] image, String ProductName, String BrandName,String Price,String Description, String Category , String WarrantyPeriod,String Request) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("image", image);
        contentValues.put("ProductName", ProductName);
        contentValues.put("BrandName", BrandName);
        contentValues.put("Price", Price);
        contentValues.put("Description", Description);
        contentValues.put("Category", Category);
        contentValues.put("WarrantyPeriod", WarrantyPeriod);
        contentValues.put("Request", Request);

        Cursor cursor=DB.rawQuery("Select*from Products where Id=?",new String[]{String.valueOf(id)});
        if (cursor.getCount()>0) {
            long result = DB.update("Products", contentValues, "Id=?", new String[]{String.valueOf(id)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }

    public ArrayList<Products> getPost() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Products> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE2, null);
        while (cursor.moveToNext()) {
            // Retrieve data from the cursor as usual
            int id = cursor.getInt(0);
            byte[] img = cursor.getBlob(1);
            String ProductName = cursor.getString(2);
            String BrandName = cursor.getString(3);
            String Price = cursor.getString(4);
            String Description = cursor.getString(5);
            String Category = cursor.getString(6);
            String WarrantyPeriod = cursor.getString(7);
            String Request = cursor.getString(8);
            int supplierId = cursor.getInt(9);

            Products products = new Products(id, img, ProductName, BrandName, Price, Description, Category, WarrantyPeriod, Request, supplierId);
            arrayList.add(products);
        }
        // Reverse the ArrayList to display data from bottom to top
        Collections.reverse(arrayList);

        return arrayList;
    }
    public ArrayList<Products> getApprovedProducts(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Products> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE2 + " WHERE Request = 'Approved'", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            byte[] img = cursor.getBlob(1);
            String ProductName = cursor.getString(2);
            String BrandName = cursor.getString(3);
            String Price = cursor.getString(4);
            String Description = cursor.getString(5);
            String Category = cursor.getString(6);
            String WarrantyPeriod = cursor.getString(7);
            String Request = cursor.getString(8);
            int supplierId = cursor.getInt(9);

            Products products = new Products(id, img, ProductName, BrandName, Price, Description, Category, WarrantyPeriod, Request, supplierId);
            arrayList.add(products);
        }
        cursor.close(); // Don't forget to close the cursor when done.
        return arrayList;
    }
    public boolean SupplierReg(byte[] image, String Company_Name, String Registration_No,String Address,String Name, String Position , String Email,String Web ,String Contact_no, String Description,String Password,String Request) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("image", image);
        contentValues.put("Company_Name", Company_Name);
        contentValues.put("Registration_No", Registration_No);
        contentValues.put("Address", Address);
        contentValues.put("Name", Name);
        contentValues.put("Position", Position);
        contentValues.put("Email", Email);
        contentValues.put("Web", Web);
        contentValues.put("Contact_no", Contact_no);
        contentValues.put("Description", Description);
        contentValues.put("Password", Password);
        contentValues.put("Request", Request);

        long result = sqLiteDatabase.insert(TABLE3, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Boolean checkSuppler(String Email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Supplier where Email = ?", new String [] {Email});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public ArrayList<Products> searchAllproducts(String keyword) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Products> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Products WHERE ProductName LIKE ? OR BrandName LIKE ? OR Price LIKE ? OR Description LIKE ? OR WarrantyPeriod LIKE ? OR Request LIKE ? OR Category LIKE ?", new String [] {"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%"});
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            byte[] img = cursor.getBlob(1);
            String ProductName = cursor.getString(2);
            String BrandName = cursor.getString(3);
            String Price = cursor.getString(4);
            String Description = cursor.getString(5);
            String Category = cursor.getString(6);
            String WarrantyPeriod = cursor.getString(7);
            String Request = cursor.getString(8);
            int supplierId=cursor.getInt(9);
            Products products = new Products(id,img, ProductName, BrandName, Price, Description,Category,WarrantyPeriod ,Request,supplierId);
            arrayList.add(products);
        }
        return arrayList;
    }
    public Boolean updatePostStatus(int id,String Request) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Request", Request);
        Cursor cursor=DB.rawQuery("Select*from Products where Id=?",new String[]{String.valueOf(id)});
        if (cursor.getCount()>0) {
            long result = DB.update("Products", contentValues, "Id=?", new String[]{String.valueOf(id)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }
    public Boolean RemoveItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Products where Id = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            long result = db.delete("Products", "Id=?", new String[]
                    {String.valueOf(id)});
            return result != -1;
        } else {
            return false;
        }
    }
    public ArrayList<Suppliers> getSuppliersPost() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Suppliers> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE3, null);
        // Move the cursor to the last position
        cursor.moveToLast();
        while (!cursor.isBeforeFirst()) {
            int id = cursor.getInt(0);
            byte[] img = cursor.getBlob(1);
            String Company_Name = cursor.getString(2);
            String Registration_No = cursor.getString(3);
            String Address = cursor.getString(4);
            String Name = cursor.getString(5);
            String Position = cursor.getString(6);
            String Email = cursor.getString(7);
            String Web = cursor.getString(8);
            String Contact_no = cursor.getString(9);
            String Description = cursor.getString(10);
            String Password = cursor.getString(11);
            String Request = cursor.getString(12);

            Suppliers suppliers = new Suppliers(id, img, Company_Name, Registration_No, Address, Name, Position, Email, Web, Contact_no, Description, Password, Request);
            arrayList.add(suppliers);

            cursor.moveToPrevious(); // Move to the previous row
        }

        return arrayList;
    }
    public ArrayList<Suppliers> searchAllsuppliers(String keyword) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Suppliers> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Supplier WHERE Company_Name LIKE ? OR Registration_No LIKE ? OR Address LIKE ? OR Name LIKE ? OR Position LIKE ? OR Email LIKE ? OR Web LIKE ? OR Contact_no LIKE ? OR Description LIKE ? OR Request LIKE ?", new String [] {"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%"});
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            byte[] img = cursor.getBlob(1);
            String Company_Name = cursor.getString(2);
            String Registration_No = cursor.getString(3);
            String Address = cursor.getString(4);
            String Name = cursor.getString(5);
            String Position = cursor.getString(6);
            String Email = cursor.getString(7);
            String Web = cursor.getString(8);
            String Contact_no = cursor.getString(9);
            String Description = cursor.getString(10);
            String Password = cursor.getString(11);
            String Request = cursor.getString(12);

            Suppliers suppliers = new Suppliers(id,img, Company_Name,Registration_No, Address, Name, Position,Email,Web,Contact_no ,Description,Password,Request);
            arrayList.add(suppliers);
        }
        return arrayList;
    }
    public Boolean updateSuppliersStatus(int id,String Request) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Request", Request);
        Cursor cursor=DB.rawQuery("Select*from Supplier where Id=?",new String[]{String.valueOf(id)});
        if (cursor.getCount()>0) {
            long result = DB.update("Supplier", contentValues, "Id=?", new String[]{String.valueOf(id)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }
    public Boolean RemoveSuppliers(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Supplier where Id = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            long result = db.delete("Supplier", "Id=?", new String[]
                    {String.valueOf(id)});
            return result != -1;
        } else {
            return false;
        }
    }
    public ArrayList<Products> SearchSupplierProduct(int SupplierId, String keyword) {
        ArrayList<Products> arrayList = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "SELECT * FROM Products WHERE (ProductName LIKE ? OR BrandName LIKE ? OR Price LIKE ? OR Description LIKE ? OR WarrantyPeriod LIKE ? OR Request LIKE ? OR Category LIKE ?) AND SupplierId = ?";
            String[] selectionArgs = new String[]{"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", String.valueOf(SupplierId)};
            Cursor cursor = db.rawQuery(query, selectionArgs);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                byte[] img = cursor.getBlob(1);
                String ProductName = cursor.getString(2);
                String BrandName = cursor.getString(3);
                String Price = cursor.getString(4);
                String Description = cursor.getString(5);
                String Category = cursor.getString(6);
                String WarrantyPeriod = cursor.getString(7);
                String Request = cursor.getString(8);
                int supplierId = cursor.getInt(9);

                Products products = new Products(id, img, ProductName, BrandName, Price, Description, Category, WarrantyPeriod, Request, supplierId);
                arrayList.add(products);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return arrayList;
    }
    public ArrayList<Products> getPostsBySupplier(int supplierId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Products> arrayList = new ArrayList<>();
        String[] selectionArgs = {String.valueOf(supplierId)};
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE2 + " WHERE SupplierId = ?", selectionArgs);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            byte[] img = cursor.getBlob(1);
            String ProductName = cursor.getString(2);
            String BrandName = cursor.getString(3);
            String Price = cursor.getString(4);
            String Description = cursor.getString(5);
            String Category = cursor.getString(6);
            String WarrantyPeriod = cursor.getString(7);
            String Request = cursor.getString(8);
            int retrievedSupplierId = cursor.getInt(9);

            Products products = new Products(id, img, ProductName, BrandName, Price, Description, Category, WarrantyPeriod, Request, retrievedSupplierId);
            arrayList.add(products);
        }
        // Reverse the ArrayList
        Collections.reverse(arrayList);
        return arrayList;
    }
    public ArrayList<Products> searchProductsByRequest(String searchQuery) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Products> arrayList = new ArrayList<>();

        String selection = "Request = ? AND (ProductName LIKE ? OR BrandName LIKE ? OR Price LIKE ? OR Description LIKE ? OR WarrantyPeriod LIKE ? OR Category LIKE ?)";
        String[] selectionArgs = new String[]{"Approved", "%" + searchQuery + "%", "%" + searchQuery + "%", "%" + searchQuery + "%", "%" + searchQuery + "%", "%" + searchQuery + "%", "%" + searchQuery + "%"};
        String[] columns = {"Id", "image", "ProductName", "BrandName", "Price", "Description", "Category", "WarrantyPeriod", "Request", "SupplierId"};
        Cursor cursor = db.query("Products", columns, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            byte[] img = cursor.getBlob(1);
            String ProductName = cursor.getString(2);
            String BrandName = cursor.getString(3);
            String Price = cursor.getString(4);
            String Description = cursor.getString(5);
            String Category = cursor.getString(6);
            String WarrantyPeriod = cursor.getString(7);
            String Request = cursor.getString(8);
            int supplierId = cursor.getInt(9);

            Products products = new Products(id, img, ProductName, BrandName, Price, Description, Category, WarrantyPeriod, Request, supplierId);
            arrayList.add(products);
        }

        return arrayList;
    }





}
