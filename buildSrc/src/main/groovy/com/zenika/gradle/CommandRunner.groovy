package com.zenika.gradle

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
class CommandRunner {

    void runCmd(String cmd, String baseDir) {
        println cmd

        def sout = new StringBuffer()
        def serr = new StringBuffer()
        def outProc = Runtime.runtime.exec(cmd, [] as String[], new File(baseDir))
        def running = true
        def bufferPrinter = {buffer ->
            def lastIndex = 0
            while (running) {
                def length = buffer.length()
                if (length > lastIndex) {
                    print buffer.subSequence(lastIndex, length)
                    lastIndex = length
                }
                Thread.sleep(100)
            }
        }
        Thread.start bufferPrinter.curry(sout)
        Thread.start bufferPrinter.curry(serr)

        outProc.consumeProcessOutput(sout, serr)
        try {
            outProc.waitFor()
        } catch (Exception e) {
            e.printStackTrace()
        }
        finally {
            running = false
        }

        if (outProc.exitValue()) {
            println "Error code: ${outProc.exitValue()}"
            System.exit(1)
        }
    }

    String runCmdWithStringReturn(String cmd, String baseDir) {
        println cmd
        def outProc = Runtime.runtime.exec(cmd, [] as String[], new File(baseDir))
        outProc.waitFor()
        String str = ""
        def stream = outProc.inputStream
        for (Byte b : stream) {
            str += (char)b
        }
        return str
    }
}
