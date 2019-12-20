package com.supergo.user.utils;

import com.jcraft.jsch.*;

/**
 * @ClassName SSH2Util
 * @Description TODO
 * @Author wesker
 * @Date 7/23/2019 3:36 PM
 * @Version 1.0
 **/
public class SSH2Util {

    private static final SSH2Util SSH_2_UTIL = new SSH2Util();

    private final String host = "39.107.94.159";

    private final String user = "root";

    private final String password = "sxk43120513Ccs";

    private final int port = 22;

    private Session session;

    public static SSH2Util getInstance() {
        return SSH_2_UTIL;
    }

    private SSH2Util() {

    }

    private void initialSession() throws Exception {
        if (session == null) {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setUserInfo(new com.jcraft.jsch.UserInfo() {
                @Override
                public String getPassphrase() {
                    return null;
                }

                @Override
                public String getPassword() {
                    return null;
                }

                @Override
                public boolean promptPassword(String s) {
                    return false;
                }

                @Override
                public boolean promptPassphrase(String s) {
                    return false;
                }

                @Override
                public boolean promptYesNo(String s) {
                    return false;
                }

                @Override
                public void showMessage(String s) {

                }
            });
            session.setPassword(password);
            session.connect();
        }
    }

    /**
     * 关闭连接
     */
    private void close() {
        if (session != null && session.isConnected()) {
            session.disconnect();
            session = null;
        }
    }

    /**
     * 上传文件
     *
     * @param remotePath 远程路径，若为空，表示当前路径，若服务器上无此目录，则会自动创建
     * @throws Exception
     */
    public void putFile(String file, String remotePath) {
        SftpATTRS attrs = null;
        String remoteFile;
        try {
            this.initialSession();
            Channel channelSftp = session.openChannel("sftp");
            channelSftp.connect();
            ChannelSftp c = (ChannelSftp) channelSftp;
            if (remotePath != null && remotePath.trim().length() > 0) {
                c.cd(remotePath);
                try {
                    attrs = c.stat(remotePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (attrs == null) {
                    c.mkdir(remotePath);
                }
                remoteFile = remotePath + "/.";
            } else {
                remoteFile = ".";
            }
            c.put(file, remoteFile);
            channelSftp.disconnect();
            this.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(String path) {
        try {
            this.initialSession();
            Channel channelSftp = session.openChannel("sftp");
            channelSftp.connect();
            ChannelSftp c = (ChannelSftp) channelSftp;
            c.rm(path);
            channelSftp.disconnect();
            this.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
