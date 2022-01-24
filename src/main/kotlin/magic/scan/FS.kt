package magic.scan

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.nameWithoutExtension

object FS {

    private val baseFolder: Path = Path.of("/srv/brscan-skey/brscan")

    fun createDirectory(name: String, then: (baseFolder: Path, tiffFolder: Path, pdfFolder: Path) -> Unit) {
        baseFolder.resolve(name).let {
            Files.createDirectory(it)
            Files.createDirectory(it.resolve("tiffs"))
            Files.createDirectory(it.resolve("pdfs"))
            then(it, it.resolve("tiffs"), it.resolve("pdfs"))
        }
    }
}

fun Path.replaceExtension(newExtensions: String): Path {
    return this.parent.resolve(this.nameWithoutExtension + "." + newExtensions)
}