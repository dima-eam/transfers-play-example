A simple REST application demonstrating the power of Play Framework in Java.

There are two problems I faced with:

1. Seems SBT itself does not treat test sources properly, so please create an IDEA project. Without it you will get all tests failed.
2. The 'dist' sbt task creates starter script which cannot be executed on Windows due to long argument string.