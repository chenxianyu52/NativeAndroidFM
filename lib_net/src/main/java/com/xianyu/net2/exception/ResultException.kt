package com.xianyu.net2.exception


class ResultException(var errCode: String?, var msg: String?) : Exception(msg)
