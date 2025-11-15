package dev.bober.store.data.receivers

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import androidx.core.net.toUri

class DownloadCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
            if (id != -1L && context != null) {
                val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val query = DownloadManager.Query().setFilterById(id)
                val cursor = downloadManager.query(query)

                if (cursor?.moveToFirst() == true) {
                    val statusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                    val status = cursor.getInt(statusIndex)

                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        val uriIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)
                        val uriString = cursor.getString(uriIndex)

                        if (uriString != null) {
                            val file = File(uriString.toUri().path!!)
                            val contentUri = FileProvider.getUriForFile(
                                context,
                                context.applicationContext.packageName + ".fileprovider",
                                file
                            )

                            val installIntent = Intent(Intent.ACTION_VIEW).apply {
                                setDataAndType(contentUri, "application/vnd.android.package-archive")
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            }
                            context.startActivity(installIntent)
                        }
                    } else {
                        Toast.makeText(context, "Загрузка не удалась", Toast.LENGTH_LONG).show()
                    }
                }
                cursor?.close()
            }
        }
    }
}