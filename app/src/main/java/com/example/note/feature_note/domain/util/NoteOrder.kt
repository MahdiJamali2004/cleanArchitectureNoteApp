package com.example.note.feature_note.domain.util

sealed class NoteOrder(val orderType: OrderType) {
     class Title(orderType: OrderType) : NoteOrder(orderType)
     class Color(orderType: OrderType) : NoteOrder(orderType)
     class DayAdded(orderType: OrderType) : NoteOrder(orderType)

     fun copy(orderType: OrderType) :NoteOrder{
         return when(this) {
               is Title -> NoteOrder.Title(orderType)
               is Color -> NoteOrder.Color(orderType)
               is DayAdded -> NoteOrder.DayAdded(orderType)
          }
     }
}