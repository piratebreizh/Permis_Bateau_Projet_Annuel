//
//  ViewListeDeroulanteExamenThematique.m
//  Permis Bateau
//
//  Created by Alexandre Dubois on 16/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//

#import "ViewHistorique.h"

@interface ViewHistorique()

@end

@implementation ViewHistorique
@synthesize managedObjectContext;



- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    // Remove table cell separator
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    
    // Assign our own backgroud for the view
    [self.tableView setBackgroundColor:[UIColor colorWithRed:213/255.0f green:230/255.0f blue:245/255.0f alpha:1.0f]];
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    
    // Add padding to the top of the table view
    UIEdgeInsets inset = UIEdgeInsetsMake(5, 0, 0, 0);
    self.tableView.contentInset = inset;
    
    id delegate = [[UIApplication sharedApplication] delegate];
    self.managedObjectContext = [delegate managedObjectContext];
    
    NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
    
    
    
    NSEntityDescription *entity = [NSEntityDescription
                                   entityForName:@"Historique" inManagedObjectContext:self.managedObjectContext];
    
    NSSortDescriptor *sortDescriptor = [[NSSortDescriptor alloc] initWithKey:@"date" ascending:NO];
    
    
    [fetchRequest setEntity:entity];
    [fetchRequest setSortDescriptors:@[sortDescriptor]];
    NSError *error;
    self.listeHistorique = [managedObjectContext executeFetchRequest:fetchRequest error:&error];
    
    
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [self.listeHistorique count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    
    if (!cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier];
    }


    UIImageView *recipeImageView = (UIImageView *)[cell viewWithTag:100];
    UILabel *recipeNameLabel = (UILabel *)[cell viewWithTag:101];
    UILabel *recipeNameLabelDescritpion = (UILabel *)[cell viewWithTag:102];
    
    // Configure the cell...
    Historique *info = [self.listeHistorique objectAtIndex:indexPath.row];

    NSString *examenBlanc=@"";
    if(info.serie.theme == nil){
        if([info.blancReussit isEqualToNumber:[NSNumber numberWithBool:NO]]){
            recipeImageView.image = [UIImage imageNamed:@"correction_cancel.png"];
        }else{
            recipeImageView.image = [UIImage imageNamed:@"tucomprendcasionsion.png"];
        }
    }else{
        examenBlanc = [NSString stringWithFormat:@"%@ - ", info.serie.theme.nom] ;
        recipeImageView.image = [UIImage imageNamed:@"livre.png"];
    }
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc]init];
    [dateFormatter setDateFormat:@"dd/MM/YYYY"];
    
    recipeNameLabel.text = [NSString stringWithFormat:@"%@ %@", examenBlanc, info.serie.nom] ;
    recipeNameLabelDescritpion.text =[NSString stringWithFormat:@"%@      %@", info.resultat, [dateFormatter stringFromDate:info.date] ];
    
    UIImage *background = [self cellBackgroundForRowAtIndexPath:indexPath];
    
    UIImageView *cellBackgroundView = [[UIImageView alloc] initWithImage:background];
    cell.backgroundColor = [UIColor clearColor];
    cellBackgroundView.image = background;
    cell.backgroundView = cellBackgroundView;

    return cell;
}

/*
 // Override to support conditional editing of the table view.
 - (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
 {
 // Return NO if you do not want the specified item to be editable.
 return YES;
 }
 */

/*
 // Override to support editing the table view.
 - (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
 {
 if (editingStyle == UITableViewCellEditingStyleDelete) {
 // Delete the row from the data source
 [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
 }
 else if (editingStyle == UITableViewCellEditingStyleInsert) {
 // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
 }
 }
 */

/*
 // Override to support rearranging the table view.
 - (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
 {
 }
 */

/*
 // Override to support conditional rearranging of the table view.
 - (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
 {
 // Return NO if you do not want the item to be re-orderable.
 return YES;
 }
 */


- (UIImage *)cellBackgroundForRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSInteger rowCount = [self tableView:[self tableView] numberOfRowsInSection:0];
    NSInteger rowIndex = indexPath.row;
    UIImage *background = nil;
    
    if (rowIndex == 0) {
        background = [UIImage imageNamed:@"cell_top.png"];
    } else if (rowIndex == rowCount - 1) {
        background = [UIImage imageNamed:@"cell_bottom.png"];
    } else {
        background = [UIImage imageNamed:@"cell_middle.png"];
    }
    
    return background;
}

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Navigation logic may go here. Create and push another view controller.
    /*
     <#DetailViewController#> *detailViewController = [[<#DetailViewController#> alloc] initWithNibName:@"<#Nib name#>" bundle:nil];
     // ...
     // Pass the selected object to the new view controller.
     [self.navigationController pushViewController:detailViewController animated:YES];
     */
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    /*ViewListeDeroulanteSerie *viewListeDeroulanteSerie = segue.destinationViewController;
    NSIndexPath *indexPath = [self.tableView indexPathForSelectedRow];
    viewListeDeroulanteSerie.theme = [self.listeThemes objectAtIndex:indexPath.item];
    viewListeDeroulanteSerie.examenThematique = YES;
    
    
    NSLog(@"Le thème : ' %@ ' a été sélectionné " ,viewListeDeroulanteSerie.theme);*/
    
}


@end