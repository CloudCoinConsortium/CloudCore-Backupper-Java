package com.cloudcore.backupper.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Command {


    /* JSON Fields */

    @Expose
    @SerializedName("command")
    public String command;
    @Expose
    @SerializedName("account")
    public String account;
    @Expose
    @SerializedName("toPath")
    public String toPath;

    public String filename;
}
