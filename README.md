# PdfRecyclerView

**DEPRECATED** 
Development has been discontinued for this repository. See [PdfViewPager][2] for PDF rendering

RecyclerView extension that can easily render vertically-scrolling PDF documents. Also downloads documents from a remote URL

*Library in progress. Current version is not ready for production !!*

Known Issues
------------

Sample app Crashes with long PDF documents. Memory management has to be improved.
The scale thing is not working as expected. [photoview][1]-related lines have been temporally commented.


TODOs
-----

- [X] Make initial Pdf scale setable by code and XML (similarly to PdfViewPager)
- [ ] Load PDF documents from SD card
- [ ] Load Remote PDF documents from URL
- [X] Pass checkstyle
- [X] UI tests


[1]: https://github.com/chrisbanes/PhotoView
[2]: https://github.com/voghDev/PdfViewPager
