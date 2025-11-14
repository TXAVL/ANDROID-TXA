package com.txahub.app.ui

import android.content.Intent
import android.os.Bundle
import android.text.format.Formatter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.txahub.app.R
import com.txahub.app.databinding.ActivityLogViewerBinding
import com.txahub.app.utils.LogWriter
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class LogViewerActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLogViewerBinding
    private lateinit var logWriter: LogWriter
    private lateinit var logAdapter: LogFileAdapter
    private var currentFilter: LogType = LogType.ALL
    
    enum class LogType(val prefix: String) {
        ALL(""),
        API("TXAAPP_api_"),
        APP("TXAAPP_app_"),
        CRASH("TXAAPP_crash_"),
        UPDATE("TXAAPP_updatecheck_"),
        PASSKEY("TXAAPP_passkey_")
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        logWriter = LogWriter(this)
        
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.txa_global_logs_title)
        
        setupUI()
        loadLogs()
    }
    
    private fun setupUI() {
        // Setup tabs
        binding.tabAll.setOnClickListener { filterLogs(LogType.ALL) }
        binding.tabApi.setOnClickListener { filterLogs(LogType.API) }
        binding.tabApp.setOnClickListener { filterLogs(LogType.APP) }
        binding.tabCrash.setOnClickListener { filterLogs(LogType.CRASH) }
        binding.tabUpdate.setOnClickListener { filterLogs(LogType.UPDATE) }
        binding.tabPasskey.setOnClickListener { filterLogs(LogType.PASSKEY) }
        
        // Setup RecyclerView
        logAdapter = LogFileAdapter { logFile ->
            showLogFileDialog(logFile)
        }
        binding.recyclerViewLogs.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewLogs.adapter = logAdapter
        
        // Hiển thị đường dẫn log folder
        binding.tvLogFolderPath.text = getString(R.string.txa_global_log_folder_path, logWriter.getLogFolderPath())
    }
    
    private fun filterLogs(logType: LogType) {
        currentFilter = logType
        updateTabSelection()
        loadLogs()
    }
    
    private fun updateTabSelection() {
        binding.tabAll.isSelected = currentFilter == LogType.ALL
        binding.tabApi.isSelected = currentFilter == LogType.API
        binding.tabApp.isSelected = currentFilter == LogType.APP
        binding.tabCrash.isSelected = currentFilter == LogType.CRASH
        binding.tabUpdate.isSelected = currentFilter == LogType.UPDATE
        binding.tabPasskey.isSelected = currentFilter == LogType.PASSKEY
    }
    
    private fun loadLogs() {
        val allLogFiles = logWriter.getAllLogFiles()
        
        val filteredFiles = if (currentFilter == LogType.ALL) {
            allLogFiles
        } else {
            allLogFiles.filter { it.name.startsWith(currentFilter.prefix) }
        }
        
        if (filteredFiles.isEmpty()) {
            binding.recyclerViewLogs.visibility = android.view.View.GONE
            binding.tvNoLogs.visibility = android.view.View.VISIBLE
            binding.tvNoLogs.text = getString(R.string.txa_global_log_no_files)
        } else {
            binding.recyclerViewLogs.visibility = android.view.View.VISIBLE
            binding.tvNoLogs.visibility = android.view.View.GONE
            logAdapter.submitList(filteredFiles)
        }
    }
    
    private fun showLogFileDialog(logFile: File) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val fileDate = dateFormat.format(Date(logFile.lastModified()))
        val fileSize = Formatter.formatFileSize(this, logFile.length())
        
        AlertDialog.Builder(this)
            .setTitle(logFile.name)
            .setMessage(
                "${getString(R.string.txa_global_log_file_size, fileSize)}\n" +
                "${getString(R.string.txa_global_log_file_date, fileDate)}"
            )
            .setPositiveButton(getString(R.string.txa_global_log_share)) { _, _ ->
                shareLogFile(logFile)
            }
            .setNeutralButton(getString(R.string.txa_global_log_delete)) { _, _ ->
                deleteLogFile(logFile)
            }
            .setNegativeButton(getString(R.string.txa_global_cancel), null)
            .show()
    }
    
    private fun shareLogFile(logFile: File) {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_STREAM, androidx.core.content.FileProvider.getUriForFile(
                    this@LogViewerActivity,
                    "${packageName}.fileprovider",
                    logFile
                ))
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivity(Intent.createChooser(intent, getString(R.string.txa_global_log_share)))
        } catch (e: Exception) {
            Toast.makeText(this, "Lỗi khi chia sẻ file: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun deleteLogFile(logFile: File) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.txa_global_log_delete))
            .setMessage(getString(R.string.txa_global_log_delete_confirm))
            .setPositiveButton(getString(R.string.txa_global_log_delete)) { _, _ ->
                if (logFile.delete()) {
                    Toast.makeText(this, "Đã xóa file log", Toast.LENGTH_SHORT).show()
                    loadLogs()
                } else {
                    Toast.makeText(this, "Không thể xóa file", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(getString(R.string.txa_global_cancel), null)
            .show()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    
    override fun onResume() {
        super.onResume()
        loadLogs()
    }
    
    private class LogFileAdapter(
        private val onItemClick: (File) -> Unit
    ) : RecyclerView.Adapter<LogFileAdapter.LogFileViewHolder>() {
        
        private val logFiles = mutableListOf<File>()
        
        fun submitList(files: List<File>) {
            logFiles.clear()
            logFiles.addAll(files)
            notifyDataSetChanged()
        }
        
        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): LogFileViewHolder {
            val view = android.view.LayoutInflater.from(parent.context)
                .inflate(R.layout.item_log_file, parent, false)
            return LogFileViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: LogFileViewHolder, position: Int) {
            holder.bind(logFiles[position])
        }
        
        override fun getItemCount() = logFiles.size
        
        inner class LogFileViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
            private val tvFileName = itemView.findViewById<android.widget.TextView>(R.id.tvFileName)
            private val tvFileSize = itemView.findViewById<android.widget.TextView>(R.id.tvFileSize)
            private val tvFileDate = itemView.findViewById<android.widget.TextView>(R.id.tvFileDate)
            
            fun bind(logFile: File) {
                tvFileName.text = logFile.name
                tvFileSize.text = android.text.format.Formatter.formatFileSize(itemView.context, logFile.length())
                
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                tvFileDate.text = dateFormat.format(Date(logFile.lastModified()))
                
                itemView.setOnClickListener {
                    onItemClick(logFile)
                }
            }
        }
    }
}

