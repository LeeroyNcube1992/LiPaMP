/* This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
   distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/.

   Copyright 2019 Gert Dewit <gert@hobbiton.be>
*/
package be.hobbiton.maven.lipamp.common;

import org.codehaus.plexus.util.StringUtils;

import java.io.File;

import static be.hobbiton.maven.lipamp.common.Constants.INVALID_MODE;
import static be.hobbiton.maven.lipamp.common.Constants.INVALID_SIZE;

public class ArchiveEntry {
    private static final char[] OCHARS = {'r', 'x', 'w'};
    private String name;
    private String absoluteName;
    private File file;
    private String userName;
    private String groupName;
    private int mode = INVALID_MODE;
    private ArchiveEntryType type;
    private long size = INVALID_SIZE;

    public ArchiveEntry(String name, File file, String userName, String groupName, int mode, ArchiveEntryType type) {
        super();
        this.name = name;
        this.absoluteName = getAbsoluteName(name);
        this.file = file;
        this.userName = userName;
        this.groupName = groupName;
        setMode(mode);
        this.type = type;
    }

    public static String stringValueOrDefault(String value, String defaultValue) {
        return (StringUtils.isNotBlank(value)) ? value.trim() : defaultValue;
    }

    public static int modeValueOrDefault(int value, int defaultValue) {
        return (value > 0) ? value : defaultValue;
    }


    static String getModeString(ArchiveEntryType type, int mode) {
        StringBuilder sb = new StringBuilder();
        sb.append(type.getRep());
        for (int i = 9; i > 0; i--) {
            char oChar = OCHARS[i % 3];
            int mask = 1 << (i - 1);
            sb.append((mode & mask) > 0 ? oChar : '-');
        }
        return sb.toString();
    }

    public static int fromModeString(String mode) {
        int modeValue = INVALID_MODE;
        if (!StringUtils.isBlank(mode)) {
            try {
                modeValue = Integer.parseInt(mode, 8);
            } catch (NumberFormatException e) {
                return INVALID_MODE;
            }
        }
        return (modeValue > 0) ? modeValue : INVALID_MODE;
    }

    public static String fromMode(int mode) {
        return Integer.toString(mode, 8);
    }

    private String getAbsoluteName(String name) {
        if (StringUtils.isNotBlank(name) && (name.charAt(0) == '.')) {
            return name.substring(1);
        }
        return name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return this.file;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getMode() {
        return this.mode;
    }

    public final void setMode(int mode) {
        this.mode = (mode > INVALID_MODE) ? mode : INVALID_MODE;
    }

    public ArchiveEntryType getType() {
        return this.type;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isValid() {
        return StringUtils.isNotBlank(this.name);
    }

    public String getAbsoluteName() {
        return absoluteName;
    }

    @Override
    public String toString() {
        return String.format("%s %8s/%-8s %8d %s", getModeString(getType(), getMode()), getUserName(), getGroupName(), getSize(), getName());
    }

    public enum ArchiveEntryType {
        F('-'), D('d'), L('-'), S('l');
        private char rep;

        ArchiveEntryType(char rep) {
            this.rep = rep;
        }

        public char getRep() {
            return this.rep;
        }

    }
}
