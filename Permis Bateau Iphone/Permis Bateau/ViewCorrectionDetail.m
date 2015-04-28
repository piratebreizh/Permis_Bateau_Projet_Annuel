//
//  ViewCorrectionDetail.m
//  Permis Bateau
//
//  Created by Alexandre Dubois on 16/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//

#import "ViewCorrectionDetail.h"
#import "Question.h"
#import "Reponse.h"
#import "ViewCorrection.h"

@interface ViewCorrectionDetail ()

@end




@implementation ViewCorrectionDetail
//@synthesize managedObjectContext;

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initialisationScreen];
    [self affichageCorrection];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)initialisationScreen{
    [self.labelEnoncer setText:self.question.enoncer];
    [self.labelReponseA setText:self.question.reponseA];
    [self.labelReponseB setText:self.question.reponseB];
    [self.labelReponseC setText:self.question.reponseC];
    [self.labelReponseD setText:self.question.reponseD];
    self.image.image =[UIImage imageNamed:self.question.image];
}

     
-(void)affichageCorrection{
    
    if(self.reponse.reponseA && [self.question.reponseCorrectA boolValue]){
        [self.boutonReponseA setBackgroundColor:[UIColor greenColor]];
        self.boutonReponseA.layer.cornerRadius = 8;
    }else if((self.reponse.reponseA && ![self.question.reponseCorrectA boolValue])||(!self.reponse.reponseA && [self.question.reponseCorrectA boolValue])){
        [self.boutonReponseA setBackgroundColor:[UIColor redColor]];
        self.boutonReponseA.layer.cornerRadius = 8;
    }
    if(self.reponse.reponseB && [self.question.reponseCorrectB boolValue]){
        [self.boutonReponseB setBackgroundColor:[UIColor greenColor]];
         self.boutonReponseB.layer.cornerRadius = 8;
    }
    else if((self.reponse.reponseB && ![self.question.reponseCorrectB boolValue])||(!self.reponse.reponseB && [self.question.reponseCorrectB boolValue])){
        [self.boutonReponseB setBackgroundColor:[UIColor redColor]];
        self.boutonReponseB.layer.cornerRadius = 8;
    }
    if(self.reponse.reponseC && [self.question.reponseCorrectC boolValue]){
        [self.boutonReponseC setBackgroundColor:[UIColor greenColor]];
        self.boutonReponseC.layer.cornerRadius = 8;
    }
    else if((self.reponse.reponseC && ![self.question.reponseCorrectC boolValue])||(!self.reponse.reponseC && [self.question.reponseCorrectC boolValue])){
        [self.boutonReponseC setBackgroundColor:[UIColor redColor]];
        self.boutonReponseC.layer.cornerRadius = 8;
    }
    if(self.reponse.reponseD && [self.question.reponseCorrectD boolValue]){
        [self.boutonReponseD setBackgroundColor:[UIColor greenColor]];
        self.boutonReponseD.layer.cornerRadius = 8;
    }
    else if((self.reponse.reponseD && ![self.question.reponseCorrectD boolValue])||(!self.reponse.reponseD && [self.question.reponseCorrectD boolValue])){
        [self.boutonReponseD setBackgroundColor:[UIColor redColor]];
        self.boutonReponseD.layer.cornerRadius = 8;
    }
    
    if([self.question.reponseCorrectA boolValue]){
        [self.labelBorderBonneReponseA setBackgroundColor:[UIColor clearColor]];
//        self.labelBorderBonneReponseA.layer.borderWidth = 2.0f;
  //      self.labelBorderBonneReponseA.layer.borderColor = [UIColor greenColor].CGColor;
        self.labelBorderBonneReponseA.layer.cornerRadius = 8;
    }
    if([self.question.reponseCorrectB boolValue]){
        [self.labelBorderBonneReponseB setBackgroundColor:[UIColor colorWithRed:15/255.0f green:163/255.0f blue:255/255.0f alpha:0.5f]];
    //    self.labelBorderBonneReponseB.layer.borderWidth = 2.0f;
    //    self.labelBorderBonneReponseB.layer.borderColor = [UIColor greenColor].CGColor;
    //    self.labelBorderBonneReponseB.layer.cornerRadius = 8;
    }
    if([self.question.reponseCorrectC boolValue]){
        [self.labelBorderBonneReponseC setBackgroundColor:[UIColor clearColor]];
        self.labelBorderBonneReponseC.layer.borderWidth = 2.0f;
        self.labelBorderBonneReponseC.layer.borderColor = [UIColor greenColor].CGColor;
        self.labelBorderBonneReponseC.layer.cornerRadius = 8;
    }
    if([self.question.reponseCorrectD boolValue]){
        [self.labelBorderBonneReponseD setBackgroundColor:[UIColor clearColor]];
        self.labelBorderBonneReponseD.layer.borderWidth = 2.0f;
        self.labelBorderBonneReponseD.layer.borderColor = [UIColor greenColor].CGColor;
        self.labelBorderBonneReponseD.layer.cornerRadius = 8;
    }

}

@end
