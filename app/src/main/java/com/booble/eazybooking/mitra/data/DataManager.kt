package com.booble.eazybooking.mitra.data

import com.booble.eazybooking.mitra.data.local.db.DbHelper
import com.booble.eazybooking.mitra.data.local.pref.PreferencesHelper
import com.booble.eazybooking.mitra.data.remote.ApiHelper

/**
 * Created by rivaldy on 10/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

interface DataManager : DbHelper, PreferencesHelper, ApiHelper