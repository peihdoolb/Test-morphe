package com.zeldrisho.patches.zalo.chat

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.string
import com.android.tools.smali.dexlib2.Opcode
import com.zeldrisho.patches.zalo.shared.Constants.COMPATIBILITY_ZALO

/**
 * Transforms outgoing GIF sends (from keyboard or gallery) into native inline chat.gif messages
 * so that receiving devices automatically autoplay the GIF in the chat feed.
 */
@Suppress("unused")
val sendGifAsInlineGifPatch = bytecodePatch(
    name = "Send GIF as inline autoplay GIF",
    description = "Forces outgoing GIF messages sent from the keyboard or gallery to be flagged as chat.gif rather than chat.photo, enabling inline autoplay on receiving clients.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_ZALO)

    execute {
        val matches = OutgoingGifDispatchFingerprint.matchAll(0..1)
        val match = matches.firstOrNull()
        if (match != null) {
            val constStringMatch = match.instructionMatches.firstOrNull { it.instruction.opcode == Opcode.CONST_STRING }
            if (constStringMatch != null) {
                match.method.replaceInstruction(
                    constStringMatch.index,
                    "const-string v0, \"chat.gif\"",
                )
            }
        }
    }
}

internal object OutgoingGifDispatchFingerprint : Fingerprint(
    returnType = "Ljava/lang/Object;",
    filters = listOf(
        string("image/gif"),
        string("gif"),
    ),
)
