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
    
    NSString *path;

    if(self.nomAppel == nil){
        self.navigationItem.title = self.coursSelectionne.nomCours;
        if(self.coursSelectionne != nil){
            if(self.coursSelectionne.id != nil){
                NSString *resourceDocPath = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex:0];
                path = [resourceDocPath
                        stringByAppendingPathComponent:[NSString stringWithFormat: @"%@.pdf",[self.coursSelectionne.id stringValue]]];
            }
        }
    }else if(!([self.nomAppel rangeOfString:@"information"].location == NSNotFound)){
        path = [[NSBundle mainBundle] pathForResource:@"InformationApplication" ofType:@"pdf"];
        self.navigationItem.title = @"Information";
    }else if(!([self.nomAppel rangeOfString:@"passerPermis"].location == NSNotFound)){
        path = [[NSBundle mainBundle] pathForResource:@"Passezvotrepermis" ofType:@"pdf"];
        self.navigationItem.title = @"Permis côtier";
    }

//            NSString *path = [[NSBundle mainBundle] pathForResource:[self.coursSelectionne.id stringValue] ofType:@"pdf"];

            if(path){
                NSURL *myUrl = [NSURL fileURLWithPath:path];
                if(myUrl){
                    NSURLRequest *myRequest = [NSURLRequest requestWithURL:myUrl];
                    [self.webViewPDF loadRequest:myRequest];
                    
                }
            }
}


@end
