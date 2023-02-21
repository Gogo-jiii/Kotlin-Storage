package com.example.storage

class Storage private constructor() {
    val internalStorage: InternalStorageManager?
        get() = InternalStorageManager.instance
    val externalStorage: ExternalStorageManager?
        get() = ExternalStorageManager.instance

    companion object {
        var instance: Storage? = null
            get() {
                if (field == null) {
                    field = Storage()
                }
                return field
            }
            private set
    }
}