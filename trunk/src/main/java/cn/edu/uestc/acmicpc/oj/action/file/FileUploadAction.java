/*
 *
 *  * cdoj, UESTC ACMICPC Online Judge
 *  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  * 	mzry1992 <@link muziriyun@gmail.com>
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package cn.edu.uestc.acmicpc.oj.action.file;

import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.ArrayUtil;
import cn.edu.uestc.acmicpc.util.Settings;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Action for file upload service, this class is a <strong>abstract</strong> class.
 * <p/>
 * If a controller need upload action, extend it from this class.
 * <p/>
 * <strong>WARN</strong>: please user {@code getParameter} to get parameter.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
 */
public abstract class FileUploadAction extends BaseAction {
    private static final long serialVersionUID = 3467163443657536111L;

    /**
     * random seed for file suffix
     */
    private static int rndSeed = 0;

    /**
     * Upload file list
     */
    private Map<String, FileItem> uploadFiles = new HashMap<String, FileItem>();

    /**
     * File item factory's size threshold.
     */
    private static final int SIZE_THRESHOLD = 1024 * 1024;

    /**
     * User defined parameter list.
     */
    protected Map<String, String[]> parameters = new HashMap<String, String[]>();

    /**
     * Get parameter from servlet request.
     *
     * @param field key name
     * @return parameter value
     */
    protected String getParameter(String field) {
        return parameters.containsKey(field) ? ArrayUtil.join(
                parameters.get(field), ",") : httpServletRequest.getParameter(field);
    }

    FileUploadAction() {
        super();
        initFiles();
    }

    /**
     * Get all files to be uploaded.
     */
    private void initFiles() {
        if (StringUtil.choose(httpServletRequest.getContentType(), "").indexOf("multipart/form-data") == -1)
            return;
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        String path = servletContext.getRealPath(Settings.SETTING_UPLOAD_FOLDER);
        diskFileItemFactory.setRepository(new File(path));
        diskFileItemFactory.setSizeThreshold(SIZE_THRESHOLD);
        ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
        try {
            List<?> list = upload.parseRequest(httpServletRequest);
            for (int i = 0; i < list.size(); i++) {
                FileItem fileItem = (FileItem) list.get(i);
                if (!fileItem.isFormField() && fileItem.get().length > 0)
                    uploadFiles.put(fileItem.getFieldName(), fileItem);
            }
        } catch (Exception e) {
        }
    }

    /**
     * Upload files and return as JSON type.
     * <p/>
     * <strong>JSON output</strong>:
     * <ul>
     * <li>
     * For success: {"result":"ok"}
     * </li>
     * <li>
     * For error: {"result":"error", "error_msg":<strong>error message</strong>}
     * </li>
     * </ul>
     * <strong>WARN</strong>: the files should be uploaded as
     * <strong>POST</strong> parameters.
     */
    public void upload() {
        json = new HashMap<String, Object>();
        request.put("fileSize", Settings.SETTING_UPLOAD_SIZE);
        request.put("fileTypes", Settings.SETTING_UPLOAD_TYPES);
        if (httpServletRequest.getMethod().equals("POST") && uploadFiles.get("file") != null) {
            FileItem fileItem = uploadFiles.get("file");
            try {
                request.put("url", handleUploadFile(fileItem, Settings.SETTING_UPLOAD_TYPES,
                        Settings.SETTING_UPLOAD_SIZE));
                request.put("name", fileItem.getName());
                request.put("extName", StringUtil.getFilenameExt(fileItem.getName()));
            } catch (AppException e) {
                json.put("result", "error");
                json.put("error_msg", e.getMessage());
            }
        }
        if (json.get("result") != null)
            json.put("result", "ok");
    }

    /**
     * Deal with upload file item
     *
     * @param fileItem
     * @param types
     * @param size
     * @return
     */
    private String handleUploadFile(FileItem fileItem, String types,
                                    Integer size) throws AppException {
        if (!StringUtil.containTypes(fileItem.getName(), types))
            throw new AppException("Can not upload this file type.");
        if (fileItem.get().length > size * SIZE_THRESHOLD)
            throw new AppException("The file is too large.");
        rndSeed = (rndSeed + 1) % 100000;
        String filename = StringUtil.generateFileName(fileItem.getName(),
                rndSeed);
        String path = ServletActionContext.getServletContext().getRealPath(
                Settings.SETTING_UPLOAD_FOLDER);
        try {
            fileItem.write(new File(path + "\\" + filename));
        } catch (Exception e) {
            return null;
        }
        return Settings.SETTING_UPLOAD_FOLDER.substring(1) + filename;
    }
}
