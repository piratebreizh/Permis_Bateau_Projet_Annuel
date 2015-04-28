//
//  ViewController.h
//  Permis Bateau
//
//  Created by Alexandre Dubois on 15/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Questionnaire.h"
#import "Historique.h"


@interface ViewCorrection : UIViewController

@property (strong, nonatomic) Questionnaire *questionnaire;
@property (nonatomic,strong) NSMutableArray *listeBouton;



@end

