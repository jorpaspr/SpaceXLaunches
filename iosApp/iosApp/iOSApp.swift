import SwiftUI
import shared

@main
struct iOSApp: App {
    let sdk = SpaceXSdk(databaseDriverFactory: DatabaseDriverFactory())
    var body: some Scene {
        WindowGroup {
            ContentView(viewModel: .init(sdk: sdk))
        }
    }
}
