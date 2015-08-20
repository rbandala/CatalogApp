package com.test.rajesh.demo.uitls;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.dropbox.sync.android.*;

import java.io.IOException;

/**
 * Created by rbandala on 7/16/2015.
 */
public class DropboxUtils {
    private DbxAccountManager mDbxAcctMgr;
    private Context context;
    static final int REQUEST_LINK_TO_DBX = 0;
    private DbxFileSystem dbxFs;

    public DropboxUtils(Context ActiviryContext, Context applicationContext){
        this.context = ActiviryContext;
        mDbxAcctMgr = DbxAccountManager.getInstance(applicationContext,"o9iyukhs2qslgsv","ba0npwr7k2bbk5c");
    }
    public boolean linkDropbox(){
        if(!mDbxAcctMgr.hasLinkedAccount()) {
            mDbxAcctMgr.startLink((Activity) context, REQUEST_LINK_TO_DBX);
            return false;
        }
        return true;
    }
    public String getDatafileContents() throws IOException {
        String contents = "";
        dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());
        DbxFile testFile = dbxFs.open(new DbxPath("data.txt"));
        try {
            contents = testFile.readString();

            Log.d("Dropbox Test", "File contents: " + contents);
        } finally {
            testFile.close();
        }
        return contents;
    }

}
