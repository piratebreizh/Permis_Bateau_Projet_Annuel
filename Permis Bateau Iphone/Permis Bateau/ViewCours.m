//
//  ViewCorrectionDetail.m
//  Permis Bateau
//
//  Created by Alexandre Dubois on 16/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//

#import "ViewCours.h"

@interface ViewCours ()

@end




@implementation ViewCours
//@synthesize managedObjectContext;

- (void)viewDidLoad {
    [super viewDidLoad];
    [self chargerLePDFdansLaVue];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void) chargerLePDFdansLaVue
{
    if(self.coursSelectionne != nil){
   
    self.webViewPDF.autoresizesSubviews = YES;
    self.webViewPDF.autoresizingMask=(UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth);
    
        if(self.coursSelectionne.nomPDF != nil){
            NSString *path = [[NSBundle mainBundle] pathForResource:self.coursSelectionne.nomPDF ofType:@"pdf"];
            if(path){
                NSURL *myUrl = [NSURL fileURLWithPath:path];
                if(myUrl){
                    NSURLRequest *myRequest = [NSURLRequest requestWithURL:myUrl];
    
                    [self.webViewPDF loadRequest:myRequest];
                    [self.view addSubview:self.webViewPDF];

                }
            }
        }
    }
    
}



@end
