package magic.scan

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.io.path.absolutePathString

fun main(args: Array<String>) {
    println("Picked up args ${args.contentToString()}")
    val folderName = "Tmp-${LocalDateTime.now().format("dd.MM.yyyy-kk:mm:ss")}"
    FS.createDirectory(folderName) { baseFolder, tiffFolder, pdfFolder ->
        val fileName = "Scan-${LocalDateTime.now().format("dd.MM.yyyy-kk:mm:ss")}.pdf"
        println("Scanning pdf(s)...")
        PDF.scan(tiffFolder, args[0], RESOLUTION.fromNumber(Integer.parseInt(args[1])))
        println("Converting pdf(s) to tiff(s)...")
        PDF.convertTiffsToPDFs(tiffFolder, pdfFolder)
        println("Merging pdf(s)...")
        PDF.mergePDFs(pdfFolder, baseFolder.resolve(fileName))
        println("Compressing pdf...")
        PDF.compressPDF(baseFolder.resolve(fileName))
        println("Done. Created pdf is at ${baseFolder.resolve(fileName).absolutePathString()}")
    }
}

fun LocalDateTime.format(format: String): String {
    return this.format(DateTimeFormatter.ofPattern(format))
}