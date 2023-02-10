package com.vjh0107.barcode.framework.test

import com.vjh0107.barcode.framework.database.config.DatabaseHost

object TestDatabaseHost {
    val get = DatabaseHost("playbn.kr", "3306", "root", "!vjh1216!vjh1216", "barcodetestdb", "test pool")
}