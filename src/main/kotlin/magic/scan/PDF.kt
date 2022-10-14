package magic.scan

import org.amshove.kluent.shouldBe
import java.nio.file.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

object PDF {

    /**
     * Convert a tiff to a pdf
     * @param file the tiff to convert
     * @return the path of the pdf file
     * */
    private fun convertTiffToPDF(tiffFile: Path, pdfFile: Path) {
        ProcessBuilder().command(
            "tiff2pdf", "-m", "0", "-p", "A4", "-o", pdfFile.absolutePathString(), tiffFile.absolutePathString()
        ).inheritIO().start().waitFor()
        shouldBe(0)
    }

    /**
     * Convert all tiffs to pdfs
     * @param files the folder containing the tiffs
     * @return a list of paths of the newly created pdfs
     * */
    fun convertTiffsToPDFs(tiffFolder: Path, pdfFolder: Path) {
        tiffFolder.listDirectoryEntries().forEach { convertTiffToPDF(it, pdfFolder.resolve(it.replaceExtension("pdf").fileName)) }
    }

    /**
     * Compress a pdf to save disk-space by linearizing it
     * This action replaces the uncompressed pdf
     * @param file The pdf to compress
     * */
    fun compressPDF(pdfFile: Path) {
        ProcessBuilder().command(
            "qpdf", "--warning-exit-0", "--no-warn", "--replace-input", "--linearize", pdfFile.absolutePathString()
        ).inheritIO().start().waitFor()
        shouldBe(0)
    }

    /**
     * Scan all pages in the document feeder and save them as tiffs
     * @param folder The folder to save the tiffs into
     * @param device The device identifier
     * @param resolution The resolution to use
     * @return The folder the tiffs were saved into
     * */
    fun scan(folder: Path, device: String, resolution: RESOLUTION): Path {
        ProcessBuilder().directory(folder.toFile()).command(
            "scanimage",
            "--device-name",
            device,
            "--resolution",
            RESOLUTION.asNumber(resolution).toString(),
            "--format=tiff",
            "--batch=Page-%04d.tiff",
            "-x",
            "210",
            "-y",
            "297"
        ).inheritIO().start().waitFor() shouldBe (0)
        return folder
    }

    /**
     * Merge the given pdfs into a single one
     * @param files the pdfs to merge
     * @return the path of the newly create pdf
     * @return
     * */
    fun mergePDFs(pdfFolder: Path, outputPdf: Path) {
        ProcessBuilder().directory(pdfFolder.toFile()).command(
            "pdfunite", *pdfFolder.listDirectoryEntries().map { it.name }.toTypedArray(), outputPdf.absolutePathString()
        ).inheritIO().start().waitFor() shouldBe (0)
    }
}