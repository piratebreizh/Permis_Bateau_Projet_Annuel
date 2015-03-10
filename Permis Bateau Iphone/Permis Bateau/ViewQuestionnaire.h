//
//  ViewQuestionnaire.h
//  Permis Bateau
//
//  Created by Alexandre Dubois on 16/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Theme.h"
#import "Questionnaire.h"

@interface ViewQuestionnaire : UIViewController

@property (weak, nonatomic) IBOutlet UILabel *labelNombreQuestion;
@property (weak, nonatomic) IBOutlet UIProgressView *barTimerQuestion;



//Theme récupéré de ViewListeDeroulanteExamenThematique lors du clique sur un des thème
@property (strong, nonatomic) Theme *theme;
@property Questionnaire *questionnaire;
@property (nonatomic, assign) BOOL examenThematique;


@property (strong, nonatomic) NSFetchedResultsController *fetchedResultsController;
@property (nonatomic,strong) NSManagedObjectContext* managedObjectContext;


@property (nonatomic, assign) float cas;
@property (strong, nonatomic) NSTimer *casovacCas;


@end

