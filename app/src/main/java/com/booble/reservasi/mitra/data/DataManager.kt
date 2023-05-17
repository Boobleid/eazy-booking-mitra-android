package com.booble.reservasi.mitra.data

import com.booble.reservasi.mitra.data.local.db.DbHelper
import com.booble.reservasi.mitra.data.local.pref.PreferencesHelper
import com.booble.reservasi.mitra.data.remote.ApiHelper

/**
 * Created by rivaldy on 10/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

interface DataManager : DbHelper, PreferencesHelper, ApiHelper