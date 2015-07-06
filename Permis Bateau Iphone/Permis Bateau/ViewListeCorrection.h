//
//  ViewListeDeroulanteExamenThematique.h
//  Permis Bateau
//
//  Created by Alexandre Dubois on 16/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//


#import <UIKit/UIKit.h>
#import "Questionnaire.h"
#import "Historique.h"
    

@interface ViewListeCorrection : UITableViewController

@property (weak, nonatomic) IBOutlet UIButton *boutonMenu;


@property (strong, nonatomic) NSFetchedResultsController *fetchedResultsController;
@property (nonatomic,strong) NSManagedObjectContext* managedObjectContext;

@property (strong, nonatomic) Serie *serieEnCours;
@property (strong, nonatomic) Questionnaire *questionnaire;

@property (nonatomic, assign) BOOL examenThematique;
@property (weak, nonatomic) IBOutlet UINavigationItem *navigationBarCorrection;

@end


