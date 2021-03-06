/* This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
   distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/.

   Copyright 2019 Gert Dewit <gert@hobbiton.be>
*/
package be.hobbiton.maven.lipamp.plugin;

import be.hobbiton.maven.lipamp.common.LinuxPackagingException;
import org.codehaus.plexus.util.StringUtils;

public class FolderEntry extends Attributable {
    private String path;

    public FolderEntry() {
        super();
    }

    public FolderEntry(String path, String username, String groupname, String mode) {
        super();
        this.path = path;
        setUsername(username);
        setGroupname(groupname);
        setMode(mode);
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isValid() {
        if(StringUtils.isNotBlank(this.path)) {
           return true;
        }
        throw new LinuxPackagingException("Invalid folder configuration");
    }

    @Override
    public String toString() {
        return String.format("path=%s u=%s g=%s m=%s", this.path, getUsername(), getGroupname(), getMode());
    }
}
