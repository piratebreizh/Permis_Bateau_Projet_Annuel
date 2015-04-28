//
//  ViewController.h
//  Permis Bateau
//
//  Created by Alexandre Dubois on 15/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Cours.h"


@interface ViewCours : UIViewController
@property (weak, nonatomic) IBOutlet UIWebView *webViewPDF;

@property (weak, nonatomic) NSString *html;

@property (weak, nonatomic) Cours *coursSelectionne;

@end

