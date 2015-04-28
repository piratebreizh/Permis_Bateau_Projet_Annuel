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

@property (weak, nonatomic) IBOutlet UILabel *labelBorderBonneReponseA;
@property (weak, nonatomic) IBOutlet UILabel *labelBorderBonneReponseB;
@property (weak, nonatomic) IBOutlet UILabel *labelBorderBonneReponseC;
@property (weak, nonatomic) IBOutlet UILabel *labelBorderBonneReponseD;

@property (weak, nonatomic) IBOutlet UIImageView *image;
@property (weak, nonatomic) IBOutlet UILabel *labelEnoncer;
@property (weak, nonatomic) IBOutlet UILabel *labelReponseA;
@property (weak, nonatomic) IBOutlet UILabel *labelReponseB;
@property (weak, nonatomic) IBOutlet UILabel *labelReponseC;
@property (weak, nonatomic) IBOutlet UILabel *labelReponseD;
@property (weak, nonatomic) IBOutlet UIButton *boutonReponseA;
@property (weak, nonatomic) IBOutlet UIButton *boutonReponseB;
@property (weak, nonatomic) IBOutlet UIButton *boutonReponseC;
@property (weak, nonatomic) IBOutlet UIButton *boutonReponseD;


@end