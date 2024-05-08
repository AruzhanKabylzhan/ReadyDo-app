package kz.rdd.store.appidentifiers

interface MockAppIdentifiersStorage {
    var deviceId: String?
    var deviceName: String?
    var sessionId: String?
    var platform: String?
    var appVersionName: String?
    fun clearAll()
}
