package Processing;/*
   DrawingRectanglesFX.java Copyright (c) Kari Laitinen

   http://www.naturalprogramming.com/

   2014-12-21 File created.
   2014-12-29 Last modification.


   With this program the user can draw rectangles to the screen.

   This program demonstrates how to react to mouse events.Lambda expressions
   are used to specify the mouse-related activities.
       
   This program is able to draw a rectangle "in a correct way" 
   regardless of the direction to which the mouse moves.
   Various drawing programs usually produce rectangles in this way.

*/

import javafx.scene.input.MouseEvent;
import javafx.scene.* ;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle ;

import java.util.concurrent.RunnableFuture;

public class DrawingRectangles {

    double starting_point_x, starting_point_y ;

    Group group_for_rectangles;

    Rectangle new_rectangle = null ;

    boolean new_rectangle_is_being_drawn = false ;

    Runnable rectangleCreatedCallback;

    public  DrawingRectangles( Scene scene, Group group, Runnable onCreation ) {

        this.group_for_rectangles = group;
        this.rectangleCreatedCallback = onCreation;
        scene.setFill( Color.BEIGE );

        scene.setOnMousePressed( ( MouseEvent event ) ->
        {
            if ( new_rectangle_is_being_drawn == false )
            {

                starting_point_x = event.getSceneX() ;
                starting_point_y = event.getSceneY() ;

                new_rectangle = new Rectangle() ;

                // A non-finished rectangle has always the same color.
                new_rectangle.setFill( Color.color(0,0,0,0) ) ; // almost white color
                new_rectangle.setStroke( Color.BLACK ) ;

                group_for_rectangles.getChildren().add( new_rectangle ) ;

                new_rectangle_is_being_drawn = true ;
            }
        } ) ;

        scene.setOnMouseDragged( ( MouseEvent event ) ->
        {
            if ( new_rectangle_is_being_drawn == true )
            {

                double current_ending_point_x = event.getSceneX() ;
                double current_ending_point_y = event.getSceneY() ;

                adjust_rectangle_properties( starting_point_x,
                        starting_point_y,
                        current_ending_point_x,
                        current_ending_point_y,
                        new_rectangle ) ;
            }
        } ) ;

        scene.setOnMouseReleased( ( MouseEvent event ) ->
        {
            if ( new_rectangle_is_being_drawn == true )
            {

                new_rectangle = null ;
                new_rectangle_is_being_drawn = false ;
                int groupSize = group_for_rectangles.getChildren().size();
                if(groupSize > 1){

                    group_for_rectangles.getChildren().remove(0,groupSize - 1);
                }
                rectangleCreatedCallback.run();
            }
        } ) ;

    }

    //  The following method adjusts coordinates so that the rectangle
    //  is shown "in a correct way" in relation to the mouse movement.

    void adjust_rectangle_properties( double starting_point_x,
                                      double starting_point_y,
                                      double ending_point_x,
                                      double ending_point_y,
                                      Rectangle given_rectangle )
    {
        given_rectangle.setX( starting_point_x ) ;
        given_rectangle.setY( starting_point_y ) ;
        given_rectangle.setWidth( ending_point_x - starting_point_x ) ;
        given_rectangle.setHeight( ending_point_y - starting_point_y ) ;

        if ( given_rectangle.getWidth() < 0 )
        {
            given_rectangle.setWidth( - given_rectangle.getWidth() ) ;
            given_rectangle.setX( given_rectangle.getX() - given_rectangle.getWidth() ) ;
        }

        if ( given_rectangle.getHeight() < 0 )
        {
            given_rectangle.setHeight( - given_rectangle.getHeight() ) ;
            given_rectangle.setY( given_rectangle.getY() - given_rectangle.getHeight() ) ;
        }
    }

}

/* NOTES:

The following links were useful when this program was developed:

https://docs.oracle.com/javase/8/javafx/api/javafx/scene/shape/Rectangle.html
https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Group.html

*/