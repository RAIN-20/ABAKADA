package com.example.abakada

import ReminderReceiver
import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.snackbar.Snackbar
import android.view.View
import java.util.Calendar
import android.widget.Toast
import com.example.abakada.auth.LoginActivity
import com.example.abakada.teacher.TeacherDashboardActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    companion object {
        private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1002
        private const val CHANNEL_ID = "simple_notification_channel"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find a view to anchor the Snackbar (usually a CoordinatorLayout or any view in the layout)
        val view = findViewById<View>(R.id.rootLayout)
        // Change this to your actual layout ID
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser: FirebaseUser? = firebaseAuth.currentUser
        if (firebaseUser != null) {
            val db = FirebaseFirestore.getInstance()
            val userRef = db.collection("users").document(firebaseUser.uid!!)

            userRef.get().addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val role = document.getString("role")

                    // Navigate to the appropriate screen based on role
                    val intent = if (role == "Teacher") {
                        Intent(this, TeacherDashboardActivity::class.java)
                    } else {
                        Intent(this, HomeScreen::class.java)
                    }
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener { exception ->
                // Handle errors in fetching user data
                Toast.makeText(baseContext, "Please Try again Later", Toast.LENGTH_SHORT).show()
            }
        }else{
            Handler().postDelayed({
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }
        // Show Snackbar
        showSnackbar(view)

        // Show Toast
        showToast("Welcome to ABAKADA")

        // Create notification channel for simple notification
        createNotificationChannel(this)

        // Show a simple notification
        showSimpleNotification()

        // Request notification permission if not granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), NOTIFICATION_PERMISSION_REQUEST_CODE)
        } else {
            // Schedule notification if permission is already granted
            scheduleNotification(this)
        }

        supportActionBar?.hide()

        // Redirect to HomeScreen after 3 seconds

    }

    private fun showSnackbar(view: View) {
        // Create and show the Snackbar
        Snackbar.make(view, "Welcome to ABAKADA", Snackbar.LENGTH_LONG)
            .setAction("Ok") {
                Snackbar.make(view, "Ok clicked", Snackbar.LENGTH_SHORT).show()
            }.show()
    }

    // Function to show a Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Function to show a simple notification
    private fun showSimpleNotification() {
        val notificationId = 1
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.abakadalogothree)  // Change this to your app's icon
            .setContentTitle("Welcome to ABAKADA")
            .setContentText("We hope you enjoy your learning journey!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)  // Dismiss the notification when clicked

        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    private fun scheduleNotification(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, 10) // First notification after 10 seconds for testing

        // Schedule the alarm to repeat every 10 seconds
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            10000,  // Repeat every 10 seconds (10000 ms)
            pendingIntent
        )
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = CHANNEL_ID
            val channelName = "Simple Notification Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = "Channel for simple notifications"
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
