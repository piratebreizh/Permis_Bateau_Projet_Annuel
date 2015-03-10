//
//  ViewListeDeroulanteExamenThematique.h
//  Permis Bateau
//
//  Created by Alexandre Dubois on 16/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//


#import <UIKit/UIKit.h>
#import "Theme.h"
#import "ViewQuestionnaire.h"




@interface ViewListeDeroulanteExamenThematique : UITableViewController

@property (strong, nonatomic) NSFetchedResultsController *fetchedResultsController;
@property (nonatomic,strong) NSManagedObjectContext* managedObjectContext;
@property (nonatomic,strong) NSArray* listeThemes;
@property (strong, nonatomic) Theme *themeSelectionne;

@end


