package com.example.feign_api.Message.Emit;

import com.example.feign_api.Pojo.BackupLog;

public class BackupMessage {
    BackupLog[] backups;

    public BackupMessage(BackupLog[] backups) {
        this.backups = backups;
    }

    public BackupLog[] getBackups() {
        return backups;
    }

    public void setBackups(BackupLog[] backups) {
        this.backups = backups;
    }
}
