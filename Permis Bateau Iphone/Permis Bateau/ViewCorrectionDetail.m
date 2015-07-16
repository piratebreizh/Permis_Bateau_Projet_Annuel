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

@interface ViewCorrectionDetail ()

@end




@implementation ViewCorrectionDetail

- (void)viewDidLoad {
    [super viewDidLoad];
        [[self navigationController] setNavigationBarHidden:NO animated:YES];
    [self initialisationScreen];
    [self affichageCorrection];

}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void) viewWillDisappear:(BOOL)animated {
    if ([self.navigationController.viewControllers indexOfObject:self]==NSNotFound) {

    }
    [super viewWillDisappear:animated];
}

-(void)initialisationScreen{
    [self.labelEnoncer setText:self.question.enoncer];
    [self.labelReponseA setText:self.question.reponseA];
    [self.labelReponseB setText:self.question.reponseB];
    [self.labelReponseC setText:self.question.reponseC];
    [self.labelReponseD setText:self.question.reponseD];

    
    NSString *nomQuestion = self.question.image;

    NSString * documentsDirectoryPath = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex:0];
    UIImage *imageQuestion = [self loadImage:nomQuestion ofType:@"png" inDirectory:documentsDirectoryPath];
    
    if(imageQuestion==nil){
        nomQuestion = [nomQuestion stringByAppendingString:@".png"];
        imageQuestion = [UIImage imageNamed:nomQuestion];
    }
    
    if(self.question.image != nil){
        self.image.image = imageQuestion;
    }
}


-(void)affichageCorrection{
    
    self.navigationItem.title = [self.question.numero stringValue];
    [self.boutonA setHidden:NO];
    [self.boutonB setHidden:NO];
    [self.boutonC setHidden:NO];
    [self.boutonD setHidden:NO];
    
    if(self.question.reponseA != nil){
        [self.boutonA setTitleColor:[UIColor blueColor] forState:UIControlStateNormal];
        self.boutonA.layer.cornerRadius = 8;
        self.boutonA.layer.borderWidth = 1.0f;
    }else{
        [self.boutonA setHidden:YES];
        [self.labelReponseA setHidden:YES];
    }
    if(self.question.reponseB != nil){
        [self.boutonB setTitleColor:[UIColor blueColor] forState:UIControlStateNormal];
        self.boutonB.layer.cornerRadius = 8;
        self.boutonB.layer.borderWidth = 1.0f;
    }else{
        [self.boutonB setHidden:YES];
        [self.labelReponseB setHidden:YES];
    }
    if(self.question.reponseC != nil){
        [self.boutonC setTitleColor:[UIColor blueColor] forState:UIControlStateNormal];
        self.boutonC.layer.cornerRadius = 8;
        self.boutonC.layer.borderWidth = 1.0f;
    }else{
        [self.boutonC setHidden:YES];
        [self.labelReponseC setHidden:YES];
    }
    if(self.question.reponseD != nil){
        [self.boutonD setTitleColor:[UIColor blueColor] forState:UIControlStateNormal];
        self.boutonD.layer.cornerRadius = 8;
        self.boutonD.layer.borderWidth = 1.0f;
    }else{
        [self.boutonD setHidden:YES];
        [self.labelReponseD setHidden:YES];
    }
    
    if([self.question.reponseCorrectA boolValue]){
        self.labelReponseA.layer.borderWidth = 2.0f;
        self.labelReponseA.layer.borderColor = [UIColor greenColor].CGColor;
        self.labelReponseA.layer.cornerRadius = 8;
    }
    if ([self.question.reponseCorrectB boolValue]){
        self.labelReponseB.layer.borderWidth = 2.0f;
        self.labelReponseB.layer.borderColor = [UIColor greenColor].CGColor;
        self.labelReponseB.layer.cornerRadius = 8;
    }
    if ([self.question.reponseCorrectC boolValue]){
        self.labelReponseC.layer.borderWidth = 2.0f;
        self.labelReponseC.layer.borderColor = [UIColor greenColor].CGColor;
        self.labelReponseC.layer.cornerRadius = 8;
    }
    if ([self.question.reponseCorrectD boolValue]){
        self.labelReponseD.layer.borderWidth = 2.0f;
        self.labelReponseD.layer.borderColor = [UIColor greenColor].CGColor;
        self.labelReponseD.layer.cornerRadius = 8;
    }
    
    if(self.reponse.reponseA){
        [self.boutonA setBackgroundColor:[UIColor colorWithRed:15/255.0f green:163/255.0f blue:255/255.0f alpha:0.5f]];
    }
    if(self.reponse.reponseB){
        [self.boutonB setBackgroundColor:[UIColor colorWithRed:15/255.0f green:163/255.0f blue:255/255.0f alpha:0.5f]];
        self.boutonB.layer.cornerRadius = 8;
        self.boutonB.layer.borderWidth = 1.0f;
    }
    if(self.reponse.reponseC){
        [self.boutonC setBackgroundColor:[UIColor colorWithRed:15/255.0f green:163/255.0f blue:255/255.0f alpha:0.5f]];
        self.boutonC.layer.cornerRadius = 8;
        self.boutonC.layer.borderWidth = 1.0f;
    }
    if(self.reponse.reponseD){
        [self.boutonD setBackgroundColor:[UIColor colorWithRed:15/255.0f green:163/255.0f blue:255/255.0f alpha:0.5f]];
        self.boutonD.layer.cornerRadius = 8;
        self.boutonD.layer.borderWidth = 1.0f;
    }

        
    if(self.reponse.reponseA && [self.question.reponseCorrectA boolValue]){
        self.labelReponseA.textContainerInset = UIEdgeInsetsMake(5, 5, 5, 5);
        [self.labelReponseA setBackgroundColor: [UIColor greenColor]];
    }else if(self.reponse.reponseA && ![self.question.reponseCorrectA boolValue]){
        self.labelReponseA.textContainerInset = UIEdgeInsetsMake(5, 5, 5, 5);
        [self.labelReponseA setBackgroundColor: [UIColor redColor]];
        self.labelReponseA.layer.borderWidth = 2.0f;
        self.labelReponseA.layer.borderColor = [UIColor redColor].CGColor;
        self.labelReponseA.layer.cornerRadius = 8;
    }
    
    if(self.reponse.reponseB && [self.question.reponseCorrectB boolValue]){
        self.labelReponseB.textContainerInset = UIEdgeInsetsMake(5, 5, 5, 5);
        [self.labelReponseB setBackgroundColor: [UIColor greenColor]];
    }else if(self.reponse.reponseB && ![self.question.reponseCorrectB boolValue]){
        self.labelReponseB.textContainerInset = UIEdgeInsetsMake(5, 5, 5, 5);
        [self.labelReponseB setBackgroundColor: [UIColor redColor]];
        self.labelReponseB.layer.borderWidth = 2.0f;
        self.labelReponseB.layer.borderColor = [UIColor redColor].CGColor;
        self.labelReponseB.layer.cornerRadius = 8;
    }

    if(self.reponse.reponseC && [self.question.reponseCorrectC boolValue]){
        self.labelReponseC.textContainerInset = UIEdgeInsetsMake(5, 5, 5, 5);
        [self.labelReponseC setBackgroundColor: [UIColor greenColor]];
    }else if(self.reponse.reponseC && ![self.question.reponseCorrectC boolValue]){
        self.labelReponseC.textContainerInset = UIEdgeInsetsMake(5, 5, 5, 5);
        [self.labelReponseC setBackgroundColor: [UIColor redColor]];
        self.labelReponseC.layer.borderWidth = 2.0f;
        self.labelReponseC.layer.borderColor = [UIColor redColor].CGColor;
        self.labelReponseC.layer.cornerRadius = 8;
    }

    if(self.reponse.reponseD && [self.question.reponseCorrectD boolValue]){
        self.labelReponseD.textContainerInset = UIEdgeInsetsMake(5, 5, 5, 5);
        [self.labelReponseD setBackgroundColor: [UIColor greenColor]];
    }else if(self.reponse.reponseD && ![self.question.reponseCorrectD boolValue]){
        self.labelReponseD.textContainerInset = UIEdgeInsetsMake(5, 5, 5, 5);
        [self.labelReponseD setBackgroundColor: [UIColor redColor]];
        self.labelReponseD.layer.borderWidth = 2.0f;
        self.labelReponseD.layer.borderColor = [UIColor redColor].CGColor;
        self.labelReponseD.layer.cornerRadius = 8;
    }
}

-(UIImage *) loadImage:(NSString *)fileName ofType:(NSString *)extension inDirectory:(NSString *)directoryPath {
    UIImage * result = [UIImage imageWithContentsOfFile:[NSString stringWithFormat:@"%@/%@.%@", directoryPath, fileName, extension]];
    
    return result;
}

@end
