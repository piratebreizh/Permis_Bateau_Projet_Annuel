//
//  ViewController.h
//  Permis Bateau
//
//  Created by Alexandre Dubois on 15/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Questionnaire.h"
#import "Reponse.h"
#import "Question.h"

@interface ViewCorrectionDetail : UIViewController


@property (strong, nonatomic) Question *question;
@property (strong, nonatomic) Reponse *reponse;

@property (weak, nonatomic) IBOutlet UIButton *boutonA;
@property (weak, nonatomic) IBOutlet UIButton *boutonB;
@property (weak, nonatomic) IBOutlet UIButton *boutonC;
@property (weak, nonatomic) IBOutlet UIButton *boutonD;


@property (weak, nonatomic) IBOutlet UITextView *labelReponseA;
@property (weak, nonatomic) IBOutlet UITextView *labelReponseB;
@property (weak, nonatomic) IBOutlet UITextView *labelReponseC;
@property (weak, nonatomic) IBOutlet UITextView *labelReponseD;



@property (weak, nonatomic) IBOutlet UITextView *labelEnoncer;
@property (weak, nonatomic) IBOutlet UIImageView *image;

@end