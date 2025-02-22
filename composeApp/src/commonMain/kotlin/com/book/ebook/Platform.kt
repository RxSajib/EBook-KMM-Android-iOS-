package com.book.ebook

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform