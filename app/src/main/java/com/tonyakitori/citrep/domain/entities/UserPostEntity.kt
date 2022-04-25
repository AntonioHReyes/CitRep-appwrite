package com.tonyakitori.citrep.domain.entities

data class UserPostEntity(
    val name: String = "",
    val avatar: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserPostEntity

        if (avatar != null) {
            if (other.avatar == null) return false
            if (!avatar.contentEquals(other.avatar)) return false
        } else if (other.avatar != null) return false

        return true
    }

    override fun hashCode(): Int {
        return avatar?.contentHashCode() ?: 0
    }
}
