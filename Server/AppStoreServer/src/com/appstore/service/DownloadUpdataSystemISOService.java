package com.appstore.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appstore.utils.DownloadFileUtil;

public class DownloadUpdataSystemISOService {

	public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
			new DownloadFileUtil().downloadFile(request, response);
	}
}
