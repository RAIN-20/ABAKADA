package com.example.abakada.teacher.tabs.quizzes

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class QuizQuestion(
    var questionType: String = "",
    var question: String = "",
    var answer: String = "",
    var imgUri: String? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(questionType)
        parcel.writeString(question)
        parcel.writeString(answer)
        parcel.writeString(imgUri)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuizQuestion> {
        override fun createFromParcel(parcel: Parcel): QuizQuestion {
            return QuizQuestion(parcel)
        }

        override fun newArray(size: Int): Array<QuizQuestion?> {
            return arrayOfNulls(size)
        }
    }
}

data class Quiz(
    var id: String = "",
    var name: String = "",
    var type: String = "",
    var difficulty: String = "",
    var questions: List<QuizQuestion> = emptyList()
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createTypedArrayList(QuizQuestion.CREATOR) ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(difficulty)
        parcel.writeTypedList(questions)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Quiz> {
        override fun createFromParcel(parcel: Parcel): Quiz {
            return Quiz(parcel)
        }

        override fun newArray(size: Int): Array<Quiz?> {
            return arrayOfNulls(size)
        }
    }
}