package com.example.e_commercelivedata.Room.MList

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.e_commercelivedata.Room.ProductCache.ProductCacheEntity

@Entity(
    tableName = "mlist",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = ProductCacheEntity::class,
            parentColumns = ["productId"],
            childColumns = ["productId"],
            onDelete = androidx.room.ForeignKey.NO_ACTION  // 🚫 don’t auto-delete
        )
    ],
    indices = [Index("productId")]
)

data class MListEntity(
    @PrimaryKey
    val productId: Int
)
