# Focus Vision

California Baptist University conducts research on glaucoma and is developing software tools to help detect pressure changes within an eye. Our project was creating a software program that supports a small USB high magnification macro camera. This program contains a graphical user interface for the user to navigate through as well as algorithms to retrieve metrics from live and still images. Since the camera has a narrow depth of field of 2mm, our program will calculate how in-focus an image or portion of a frame is in order to let the user know when to take a picture.

## Getting Started

Clone and download the project. 

### Prerequisites

You need to provide your own OpenCV library and (dll for windows pr dylib for mac) file for the project. 
* If you already have the OpenCV library, great just add it to the project and run the project from AppEntry
* If you do not already have OpenCV you will have to get it. See [this page](http://opencv-java-tutorials.readthedocs.io/en/latest/01-installing-opencv-for-java.html)for how to get it.


### Building

Run AppEntry


## Built With

* [OpenCV](https://docs.opencv.org/3.3.1/) - The image and camera framework used
* [JavaFX](http://www.oracle.com/technetwork/java/javase/overview/javafx-overview-2158620.html) - The UI framework used
* [Java](http://www.oracle.com/technetwork/java/index.html) - The language

## Information about the metrics
* We calculate the variance of the result of the [Laplace Operator](https://docs.opencv.org/3.0-alpha/doc/tutorials/imgproc/imgtrans/laplace_operator/laplace_operator.html) of OpenCV to give us our focus metric. 
* We utilise [Median](https://docs.opencv.org/3.0-alpha/doc/py_tutorials/py_imgproc/py_filtering/py_filtering.html?#median-blurring) and [Gausian](https://docs.opencv.org/3.0-alpha/doc/py_tutorials/py_imgproc/py_filtering/py_filtering.html?#gaussian-blurring) blurs provided by OpenCV
* We calculate the [Michelson and RMS Contrast](https://en.wikipedia.org/wiki/Contrast_(vision)). 


## Authors

* **Aarin Renfroe** - *Project Lead - [LinkedIn](https://www.linkedin.com/in/aaron-renfroe/)
* **Richard Christensen** - *Team Member* - [Email](richardarsha@yahoo.com)
* **Jp Syfacunda** - *Team Member* - [LinkedIn](https://www.linkedin.com/in/john-syfacunda-10a917106/)


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Python and cv2 for quick prototyping and development
* Stack Overflow
* Department Chair and Advisors
