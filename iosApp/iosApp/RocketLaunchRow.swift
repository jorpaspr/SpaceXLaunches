import SwiftUI
import shared

struct RocketLaunchRow: View {
    var rocketLaunch: RocketLaunch
    
    var body: some View {
        HStack() {
            VStack(alignment: .leading, spacing: 10.0) {
                Text("Launch name: \(rocketLaunch.missionName)")
                Text(launchText).foregroundColor(launchColor)
                Text("Launch year: \(String(rocketLaunch.launchYear))")
                Text("Launch details: \(rocketLaunch.details ?? "")")
            }
            Spacer()
        }
    }
}

extension RocketLaunchRow {
    private var launchText: String {
        if let isSuccess = rocketLaunch.launchSuccess {
            return isSuccess.boolValue ? "Successful" : "Unsuccessful"
        } else {
            return "No data"
        }
    }
    
    private var launchColor: Color {
        if let isSuccess = rocketLaunch.launchSuccess {
            return isSuccess.boolValue ? Color.green : Color.red
        } else {
            return Color.gray
        }
    }
}

extension ContentView {
    enum LoadableLaunches {
        case loading
        case result([RocketLaunch])
        case error(String)
    }
    
    class ViewModel: ObservableObject {
        let sdk: SpaceXSdk
        @Published var launches = LoadableLaunches.loading
        
        init(sdk: SpaceXSdk) {
            self.sdk = sdk
            self.loadLaunches(forceReload: false)
        }
        
        func loadLaunches(forceReload: Bool) {
            self.launches = .loading
            sdk.getLaunches(forceReload: forceReload, completionHandler: { launches, error in
                if let launches = launches {
                    self.launches = .result(launches)
                } else {
                    self.launches = .error(error?.localizedDescription ?? "error")
                }
            })
        }
    }
}
