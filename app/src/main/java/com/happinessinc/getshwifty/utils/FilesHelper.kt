package com.happinessinc.getshwifty.utils

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import java.io.File


fun Context.downloadFile(uRl: String){
    val myDir = File(Environment.DIRECTORY_DOCUMENTS, "EPODS/")
    if (!myDir.exists()) {
        myDir.mkdirs()
    }
    val mgr: DownloadManager =
        this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val downloadUri: Uri = Uri.parse(uRl)
    val request: DownloadManager.Request = DownloadManager.Request(
        downloadUri
    )
    request.setAllowedNetworkTypes(
        DownloadManager.Request.NETWORK_WIFI
                or DownloadManager.Request.NETWORK_MOBILE
    ).setAllowedOverMetered(true)
        .setAllowedOverRoaming(true).setTitle("Downloading EPODs")
        .setVisibleInDownloadsUi(true)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS, uRl)
    mgr.enqueue(request)
}
fun Context.downloadFile(uRl: String, dirName: String){
    val myDir = File(Environment.DIRECTORY_PICTURES, "$dirName")
    if (!myDir.exists()) {
        myDir.mkdirs()
    }
    val mgr: DownloadManager =
        this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val downloadUri: Uri = Uri.parse(uRl)
    val request: DownloadManager.Request = DownloadManager.Request(
        downloadUri
    )
    request.setAllowedNetworkTypes(
        DownloadManager.Request.NETWORK_WIFI
                or DownloadManager.Request.NETWORK_MOBILE
    ).setAllowedOverMetered(true)
        .setAllowedOverRoaming(true).setTitle("Downloading $dirName")
        .setVisibleInDownloadsUi(true)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uRl)
    mgr.enqueue(request)
}
