package com.example.hypercareapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BloodPressureViewModel : ViewModel() {
    private val _history = MutableStateFlow<List<BloodPressureRecords>>(emptyList())
    val history: StateFlow<List<BloodPressureRecords>> = _history

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        fetchRecords()
    }

    private fun fetchRecords() {
        _isLoading.value = true

        val userId = getCurrentUserId()

        if (userId != null) {
            val firestore = Firebase.firestore
            firestore.collection("bloodPressureRecords")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener { documents ->
                    val recordsList = documents.map { doc ->
                        BloodPressureRecords(
                            systolic = doc.getLong("systolic") ?: 0L,
                            diastolic = doc.getLong("diastolic") ?: 0L,
                            result = doc.getString("result") ?: "",
                            recommendation = doc.getString("recommendation") ?: "",
                            timestamp = doc.getTimestamp("timestamp"),
                            userId = doc.getString("userId") ?: ""
                        )
                    }
                    _history.value = recordsList
                    _isLoading.value = false
                }
                .addOnFailureListener { exception ->
                    Log.e("BloodPressureViewModel", "Error fetching records: ${exception.message}")
                    _isLoading.value = false
                }
        } else {
            _history.value = emptyList()
            _isLoading.value = false
        }
    }

    internal fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    fun addRecord(record: BloodPressureRecords) {
        val firestore = Firebase.firestore
        val newRecordRef = firestore.collection("bloodPressureRecords").document()

        newRecordRef.set(
            mapOf(
                "systolic" to record.systolic,
                "diastolic" to record.diastolic,
                "result" to record.result,
                "recommendation" to record.recommendation,
                "timestamp" to record.timestamp,
                "userId" to getCurrentUserId()
            )
        ).addOnSuccessListener {
            Log.d("BloodPressureViewModel", "Record added successfully")
            // Fetch records again after adding the new one to update the history immediately
            fetchRecords()
        }.addOnFailureListener { exception ->
            Log.e("BloodPressureViewModel", "Error adding record: ${exception.message}")
            // Consider showing a toast or other feedback to the user here
        }
    }

    fun clearHistory() {
        val userId = getCurrentUserId()
        if (userId != null) {
            val firestore = Firebase.firestore
            firestore.collection("bloodPressureRecords")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener { documents ->
                    val batch = firestore.batch()
                    for (document in documents) {
                        batch.delete(document.reference)
                    }
                    batch.commit().addOnSuccessListener {
                        Log.d("BloodPressureViewModel", "All records deleted successfully")
                        _history.value = emptyList()
                    }.addOnFailureListener { exception ->
                        Log.e("BloodPressureViewModel", "Error deleting records: ${exception.message}")
                        // You could display user-friendly error here
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("BloodPressureViewModel", "Error fetching records for deletion: ${exception.message}")
                    // Display a message or handle the error as needed
                }
        } else {
            Log.e("BloodPressureViewModel", "No user logged in, cannot clear history")
            // Handle the case where the user is not logged in
        }
    }
}
