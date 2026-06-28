import AVFoundation
import Foundation

guard CommandLine.arguments.count == 3 else {
    fputs("Usage: swift scripts/compress_video.swift <source> <output.mp4>\n", stderr)
    exit(2)
}

let sourceURL = URL(fileURLWithPath: CommandLine.arguments[1])
let outputURL = URL(fileURLWithPath: CommandLine.arguments[2])
let asset = AVURLAsset(url: sourceURL)

let compatiblePresets = AVAssetExportSession.exportPresets(compatibleWith: asset)
let preferredPresets = [
    AVAssetExportPresetLowQuality,
    AVAssetExportPreset960x540,
    AVAssetExportPresetMediumQuality,
    AVAssetExportPreset1280x720
]

guard let preset = preferredPresets.first(where: compatiblePresets.contains) else {
    fputs("No compatible export preset found for \(sourceURL.path)\n", stderr)
    exit(1)
}

guard let exporter = AVAssetExportSession(asset: asset, presetName: preset) else {
    fputs("Could not create AVAssetExportSession.\n", stderr)
    exit(1)
}

let fileTypes = exporter.supportedFileTypes
let outputType: AVFileType
if fileTypes.contains(.mp4) {
    outputType = .mp4
} else if fileTypes.contains(.m4v) {
    outputType = .m4v
} else if fileTypes.contains(.mov) {
    outputType = .mov
} else {
    fputs("No supported MP4/M4V/MOV output type found. Supported: \(fileTypes)\n", stderr)
    exit(1)
}

try? FileManager.default.removeItem(at: outputURL)
exporter.outputURL = outputURL
exporter.outputFileType = outputType
exporter.shouldOptimizeForNetworkUse = true

let semaphore = DispatchSemaphore(value: 0)
exporter.exportAsynchronously {
    semaphore.signal()
}
semaphore.wait()

switch exporter.status {
case .completed:
    let size = (try? FileManager.default.attributesOfItem(atPath: outputURL.path)[.size] as? NSNumber)?.int64Value ?? 0
    print("Exported \(outputURL.path)")
    print("Preset: \(preset)")
    print("Output type: \(outputType.rawValue)")
    print("Size: \(size) bytes")
case .failed, .cancelled:
    fputs("Export failed: \(exporter.error?.localizedDescription ?? "unknown error")\n", stderr)
    exit(1)
default:
    fputs("Export ended with status \(exporter.status.rawValue)\n", stderr)
    exit(1)
}
